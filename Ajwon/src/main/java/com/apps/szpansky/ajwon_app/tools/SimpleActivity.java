package com.apps.szpansky.ajwon_app.tools;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.apps.szpansky.ajwon_app.R;


public abstract class SimpleActivity extends AppCompatActivity {

    protected static Database myDB;
    protected Bundle toNextActivityBundle = new Bundle();
    protected SimpleCursorAdapter myCursorAdapter;
    protected ListView listView;
    protected FloatingActionButton addButton;
    private AbsListView.OnScrollListener onScrollListener;
    protected Toolbar toolbar;
    private Data data;


    protected abstract void onAddButtonClick();


    protected SimpleActivity(Data data) {
        this.data = data;
    }


    protected ListView setListView() {
        return ((ListView) findViewById(R.id.list_view_simple_view));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_view);

        addButton = (FloatingActionButton) findViewById(R.id.add);

        myDB = new Database(this);

        setToolBar();

        listView = setListView();
        refreshListView();
        onAddButtonClick();
        onScrolling();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.contains("\"")) {
                    data.filter = "";
                    Toast.makeText(SimpleActivity.this, R.string.search_toast, Toast.LENGTH_SHORT).show();
                } else {
                    data.filter = newText;
                }

                refreshListView();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        refreshListView();
    }


    private void setToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    protected void refreshListView() {
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(),
                data.getItemLayoutResourceId(),
                data.setCursor(myDB),
                data.getFromFieldsNames(),
                data.getToViewIDs(),
                0);
        listView.setAdapter(myCursorAdapter);
        listView.setOnScrollListener(onScrollListener);
    }


    protected void popupForDelete(final int id) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        data.deleteData(id, myDB);
                        refreshListView();

                        break;
                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_notify).setPositiveButton(R.string.agree, dialogClickListener)
                .setNegativeButton(R.string.disagree, dialogClickListener).show();

    }


    private void onScrolling() {
        onScrollListener = new AbsListView.OnScrollListener() {
            int previousItem = 0;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (previousItem != firstVisibleItem) {
                    if (previousItem < firstVisibleItem) {
                        addButton.hide();
                    } else {
                        addButton.show();
                    }
                    previousItem = firstVisibleItem;
                }
            }
        };
        onScrollListener.onScroll(listView, listView.getFirstVisiblePosition(), listView.getLastVisiblePosition(), listView.getCount());
    }

}



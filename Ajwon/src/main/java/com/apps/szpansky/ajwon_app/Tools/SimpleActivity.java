package com.apps.szpansky.ajwon_app.Tools;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.apps.szpansky.ajwon_app.R;


public abstract class SimpleActivity extends AppCompatActivity {

    protected static Database myDB;
    protected Bundle toNextActivityBundle = new Bundle();
    protected SimpleCursorAdapter myCursorAdapter;
    protected ListView listView;

    private Data data;

    public SimpleActivity(Data data) {
        this.data = data;
    }


    protected ListView setListView() {
        return ((ListView) findViewById(R.id.list_view_simple_view));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(data.getLayoutResourceId());

        myDB = new Database(this);

        listView = setListView();
        refreshListView();
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        refreshListView();
    }


    public void refreshListView() {
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(),
                data.getItemLayoutResourceId(),
                data.setCursor(myDB),
                data.getFromFieldsNames(),
                data.getToViewIDs(),
                0);
        listView.setAdapter(myCursorAdapter);
    }


    public void popupForDelete(final int id) {       //TODO send object that got method for delete

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
        builder.setMessage("You want to delete?\nIt will delete all depends data").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }
}



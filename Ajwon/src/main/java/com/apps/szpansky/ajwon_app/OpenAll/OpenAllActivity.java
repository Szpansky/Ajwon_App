package com.apps.szpansky.ajwon_app.OpenAll;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.apps.szpansky.ajwon_app.Tools.Database;


public abstract class OpenAllActivity extends AppCompatActivity {

    protected Bundle toNextActivity = new Bundle();

    protected Database myDB;
    protected Cursor cursor;
    protected String[] fromFieldsNames;
    protected int[] toViewIDs;
    protected SimpleCursorAdapter myCursorAdapter;
    protected ListView listView;

    protected String table;
    protected String[] allKeys;
    protected String rowWhereId;

    protected abstract int getLayoutResourceId();
    protected abstract int getItemLayoutResourceId();

    protected abstract void setTable(String table);
    protected abstract void setAllKeys(String[] allKeys);
    protected abstract void setRowWhereId(String rowWhereId);
    protected abstract void setToViewIDs(int[] toViewIDs);
    protected abstract void setListView(ListView listView);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDB = new Database(this);
        setContentView(getLayoutResourceId());
        setTable(table);
        setAllKeys(allKeys);
        setRowWhereId(rowWhereId);
        setToViewIDs(toViewIDs);
        setListView(listView);
        refreshListView();
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        refreshListView();
    }


    public void refreshListView(){
        cursor = myDB.getAllRows(table, allKeys, rowWhereId);
        fromFieldsNames = allKeys;
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), getItemLayoutResourceId(), cursor, fromFieldsNames, toViewIDs, 0);
        listView.setAdapter(myCursorAdapter);
    }


    public void popup(final long id) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        myDB.delete(table, id, rowWhereId);
                        refreshListView();

                        break;
                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You want to delete?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }


}



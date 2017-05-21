package com.apps.szpansky.ajwon_app.Tools;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;



public abstract class SimpleActivity extends AppCompatActivity {

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

    protected abstract Cursor setCursor();
    protected abstract String setTable();
    protected abstract String[] setAllKeys();
    protected abstract String setRowWhereId();
    protected abstract int[] setToViewIDs();
    protected abstract ListView setListView();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDB = new Database(this);
        setContentView(getLayoutResourceId());
        table = setTable();
        allKeys = setAllKeys();
        rowWhereId = setRowWhereId();
        toViewIDs = setToViewIDs();
        cursor = setCursor();
        listView = setListView();
        refreshListView();
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        refreshListView();
    }


    public void refreshListView(){
        cursor = setCursor();
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



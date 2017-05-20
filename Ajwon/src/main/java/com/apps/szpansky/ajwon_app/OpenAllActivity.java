package com.apps.szpansky.ajwon_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public abstract class OpenAllActivity extends AppCompatActivity {


    public Database myDB;
    public Cursor cursor;
    public String[] fromFieldsNames;
    public int[] toViewIDs;
    public SimpleCursorAdapter myCursorAdapter;
    public ListView listView;

    public String table;
    public String[] allKeys;
    public String rowWhereId;

    protected abstract int getLayoutResourceId();
    protected abstract int getItemLayoutResourceId();

    public abstract void setTable(String table);
    public abstract void setAllKeys(String[] allKeys);
    public abstract void setRowWhereId(String rowWhereId);
    public abstract void setToViewIDs(int[] toViewIDs);
    public abstract void setListView(ListView listView);

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



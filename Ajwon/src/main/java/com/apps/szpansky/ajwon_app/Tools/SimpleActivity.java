package com.apps.szpansky.ajwon_app.Tools;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;



public abstract class SimpleActivity extends AppCompatActivity {

    public SimpleActivity(Data data) {
        this.data = data;
    }

    protected static Database myDB;
    private Data data;




    protected Bundle toNextActivity = new Bundle();

    protected Cursor cursor;
    protected String[] fromFieldsNames;
    protected int[] toViewIDs;
    protected SimpleCursorAdapter myCursorAdapter;
    protected ListView listView;

    protected String table;
    protected String[] allKeys;
    protected String rowWhereId;


    protected abstract ListView setListView();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDB = new Database(this);

        setContentView(data.getLayoutResourceId());
        table = data.setTable();
        allKeys = data.setAllKeys();
        rowWhereId = data.setRowWhereId();
        toViewIDs = data.setToViewIDs();
        cursor = data.setCursor(myDB);
        listView = setListView();
        fromFieldsNames = data.setFromFieldsNames();
        refreshListView();
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        refreshListView();
    }


    public void refreshListView() {
        cursor = data.setCursor(myDB);
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), data.getItemLayoutResourceId(), cursor, fromFieldsNames, toViewIDs, 0);
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



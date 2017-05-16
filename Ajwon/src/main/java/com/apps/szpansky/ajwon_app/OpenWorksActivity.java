package com.apps.szpansky.ajwon_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class OpenWorksActivity extends AppCompatActivity {


    @Override
    protected void onStart() {
        super.onStart();
        myDB = new Database(this);
        cursor = myDB.getAllRows(Database.TABLE_WORKS, Database.ALL_KEYS_WORK, Database.WORK_CATALOG_NR);
        fromFieldsNames = Database.ALL_KEYS_WORK;
        toViewIDs = new int[]{R.id.catalogId, R.id.date};
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.item_work_view, cursor, fromFieldsNames, toViewIDs, 0);
        workView = (ListView) findViewById(R.id.list_view_catalogs);
    }


    Database myDB;
    Cursor cursor;
    String[] fromFieldsNames;
    int[] toViewIDs;
    SimpleCursorAdapter myCursorAdapter;
    ListView workView;


    @Override
    protected void onPostResume() {
        super.onPostResume();
        workView.setAdapter(myCursorAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_works);

        addData();
        listViewItemClick();

    }


    public void addData() {
        Button add = (Button) findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent Intent_Add_Work = new Intent(OpenWorksActivity.this, AddWorkActivity.class);
                OpenWorksActivity.this.startActivity(Intent_Add_Work);
            }
        });
    }


    private void popup(final long id) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        myDB.delete(Database.TABLE_WORKS, id, Database.WORK_CATALOG_NR);
                        finish();
                        startActivity(getIntent());
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


    private void listViewItemClick() {
        final ListView myList = (ListView) findViewById(R.id.list_view_catalogs);
        final Boolean[] flag = new Boolean[1];
        flag[0] = true;

        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                flag[0] = false;
                popup(id);
                return false;
            }
        });

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (flag[0]) {
                    Intent Intent_Open_Orders = new Intent(OpenWorksActivity.this, OpenOrdersActivity.class);
                    Bundle b = new Bundle();
                    b.putLong("id", id);
                    Intent_Open_Orders.putExtras(b);
                    OpenWorksActivity.this.startActivity(Intent_Open_Orders);
                }
                flag[0] = true;
            }
        });
    }
}

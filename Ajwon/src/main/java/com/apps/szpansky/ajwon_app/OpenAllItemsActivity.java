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

public class OpenAllItemsActivity extends AppCompatActivity {


    private Database myDB;
    private Cursor cursor;
    private String[] fromFieldsNames;
    private int[] toViewIDs;
    private SimpleCursorAdapter myCursorAdapter;
    private ListView itemView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_items);

        listViewItemClick();
        addData();
    }



    @Override
    protected void onStart() {
        super.onStart();
        myDB = new Database(this);
        cursor = myDB.getAllRows(Database.TABLE_ITEMS, Database.ALL_KEYS_ITEMS, Database.ITEM_CATALOG_NR);
        fromFieldsNames = Database.ALL_KEYS_ITEMS;
        toViewIDs = new int[]{R.id.itemId, R.id.itemName, R.id.itemPrice, R.id.itemDiscount};
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.item_item_view, cursor, fromFieldsNames, toViewIDs, 0);
        itemView = (ListView) findViewById(R.id.list_view_items);
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        itemView.setAdapter(myCursorAdapter);
    }


    public void addData(){
        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent_Add_Item = new Intent(OpenAllItemsActivity.this, AddEditItemsActivity.class);
                OpenAllItemsActivity.this.startActivity(Intent_Add_Item);
            }
        });
    }


    private void popup(final long id) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        myDB.delete(Database.TABLE_ITEMS, id, Database.ITEM_CATALOG_NR);
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


    private void listViewItemClick() {
        final ListView myList = (ListView) findViewById(R.id.list_view_items);
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
                    Intent intent = new Intent(OpenAllItemsActivity.this, AddEditItemsActivity.class);

                    Bundle b = new Bundle();
                    b.putBoolean("edit", true);
                    b.putLong("id", id);
                    intent.putExtras(b);

                    OpenAllItemsActivity.this.startActivity(intent);
                }
                flag[0] = true;
            }
        });
    }


    private void refreshListView(){
        cursor = myDB.getAllRows(Database.TABLE_ITEMS, Database.ALL_KEYS_ITEMS, Database.ITEM_CATALOG_NR);
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.item_item_view, cursor, fromFieldsNames, toViewIDs, 0);
        itemView.setAdapter(myCursorAdapter);
    }



}

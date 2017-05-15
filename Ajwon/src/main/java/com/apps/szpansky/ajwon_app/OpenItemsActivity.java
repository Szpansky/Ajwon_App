package com.apps.szpansky.ajwon_app;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class OpenItemsActivity extends AppCompatActivity {


    Database myDB = new Database(this);

    @Override
    protected void onPostResume() {
        super.onPostResume();
        populateListView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_items);
        populateListView();
        listViewItemDelClick();
        addData();


    }


    public void addData(){
        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent_Add_Item = new Intent(OpenItemsActivity.this, AddItemsActivity.class);
                OpenItemsActivity.this.startActivity(Intent_Add_Item);
            }
        });
    }


    private void populateListView() {
        Cursor cursor = myDB.getAllRows(Database.TABLE_ITEMS, Database.ALL_KEYS_ITEMS, Database.ITEM_CATALOG_NR);
        String[] fromFieldsNames = Database.ALL_KEYS_ITEMS;
        int[] toViewIDs = new int[]{R.id.itemId, R.id.itemName, R.id.itemPrice, R.id.itemDiscount};
        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.item_item_view, cursor, fromFieldsNames, toViewIDs, 0);
        ListView workView = (ListView) findViewById(R.id.list_view_items);
        workView.setAdapter(myCursorAdapter);
    }

    private void listViewItemDelClick() {
        final ListView myList = (ListView) findViewById(R.id.list_view_items);
        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                myDB.delete(Database.TABLE_ITEMS, id, Database.ITEM_CATALOG_NR);
                populateListView();
                return false;
            }
        });

    }


}

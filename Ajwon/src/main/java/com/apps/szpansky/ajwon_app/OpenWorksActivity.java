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


public class OpenWorksActivity extends AppCompatActivity {

Database myDB = new Database(this);


    @Override
    protected void onPostResume() {
        super.onPostResume();
        populateListView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_works);
        addData();
        populateListView();
        listViewItemDelClick();
        listViewItemClick();
    }



    public void addData(){
        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent Intent_Add_Work = new Intent(OpenWorksActivity.this, AddWorkActivity.class);
                OpenWorksActivity.this.startActivity(Intent_Add_Work);
            }
        });
    }


    private void populateListView(){
        Cursor cursor = myDB.getAllRows(Database.TABLE_WORKS,Database.ALL_KEYS_WORK, Database.WORK_CATALOG_NR);
        String[] fromFieldsNames = Database.ALL_KEYS_WORK;
        int[] toViewIDs = new int[] {R.id.catalogId, R.id.date};
        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.item_work_view, cursor, fromFieldsNames, toViewIDs, 0);
        ListView workView = (ListView) findViewById(R.id.list_view_catalogs);
        workView.setAdapter(myCursorAdapter);
    }

    private void listViewItemDelClick(){
        final ListView myList = (ListView) findViewById(R.id.list_view_catalogs);
        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                myDB.delete(Database.TABLE_WORKS,id,Database.WORK_CATALOG_NR);
                populateListView();
                return false;
            }
        });

    }


    private void listViewItemClick(){
        final ListView myList = (ListView) findViewById(R.id.list_view_catalogs);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent Intent_Open_Orders = new Intent(OpenWorksActivity.this, OpenOrdersActivity.class );

                Bundle b = new Bundle();
                b.putLong("id", id);
                Intent_Open_Orders.putExtras(b);

                OpenWorksActivity.this.startActivity(Intent_Open_Orders);

            }
        });
    }


}

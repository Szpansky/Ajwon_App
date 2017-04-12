package com.apps.szpansky.avon_app;

import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;


public class OpenWorks extends AppCompatActivity {

Database myDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_works);
        myDB = new Database(this);
       // populateListView();
        Add_Data();



    }

    public void Add_Data(){
        Button button_add_order = (Button) findViewById(R.id.add_work);
        button_add_order.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent Intent_Add_Work = new Intent(OpenWorks.this, AddWork.class);
                OpenWorks.this.startActivity(Intent_Add_Work);
            }
        });

    }


    public void populateListView(){

        Cursor cursor = myDB.getWORK_ROWS();
        String[] fromFieldsNames = new String[] {Database.WORK_CATALOG_NR,Database.WORK_DATE};
        int[] toViewIDs = new int[] {R.id.catalog_id, R.id.order_date};
        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.item_work_view, cursor, fromFieldsNames, toViewIDs, 0);
        ListView workView = (ListView) findViewById(R.id.list_view_catalogs);
        workView.setAdapter(myCursorAdapter);
    }


 
}

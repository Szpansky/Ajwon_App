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

public class OpenPersonsActivity extends AppCompatActivity {

    Database myDB = new Database(this);



    @Override
    protected void onPostResume() {
        super.onPostResume();
        populateListView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_persons);
        addData();
        populateListView();
        listViewItemDelClick();
    }

    public void addData(){
        Button button_add_order = (Button) findViewById(R.id.add_person);
        button_add_order.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent Intent_Add_Person = new Intent(OpenPersonsActivity.this, AddPersonActivity.class);
                OpenPersonsActivity.this.startActivity(Intent_Add_Person);
            }
        });
    }



    private void populateListView(){
        Cursor cursor = myDB.getAllRows(Database.TABLE_PERSONS,Database.ALL_KEYS_PERSONS, Database.PERSON_ID);
        String[] fromFieldsNames = new String[] {Database.PERSON_ID,Database.PERSON_NAME,Database.PERSON_SURNAME,Database.PERSON_PHONE,Database.PERSON_ADDRESS};
        int[] toViewIDs = new int[] {R.id.person_id, R.id.person_name,R.id.person_surname,R.id.person_phone,R.id.person_address};
        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.item_person_view, cursor, fromFieldsNames, toViewIDs, 0);
        ListView workView = (ListView) findViewById(R.id.list_view_persons);
        workView.setAdapter(myCursorAdapter);
    }

    private void listViewItemDelClick(){
        final ListView myList = (ListView) findViewById(R.id.list_view_persons);
        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                myDB.delete(Database.TABLE_PERSONS,id,Database.PERSON_ID);
                populateListView();
                return false;
            }
        });

    }



}

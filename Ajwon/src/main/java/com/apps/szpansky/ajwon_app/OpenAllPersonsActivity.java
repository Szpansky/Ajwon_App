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

public class OpenAllPersonsActivity extends AppCompatActivity {


    private Database myDB;
    private Cursor cursor;
    private String[] fromFieldsNames;
    private int[] toViewIDs;
    private SimpleCursorAdapter myCursorAdapter;
    private ListView personView;



    @Override
    protected void onPostResume() {
        super.onPostResume();
        personView.setAdapter(myCursorAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        myDB = new Database(this);
        cursor = myDB.getAllRows(Database.TABLE_PERSONS, Database.ALL_KEYS_PERSONS, Database.PERSON_ID);
        fromFieldsNames = Database.ALL_KEYS_PERSONS;
        toViewIDs = new int[] {R.id.personId, R.id.personName,R.id.personSurname,R.id.personPhone,R.id.personAddress};
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.item_person_view, cursor, fromFieldsNames, toViewIDs, 0);
        personView = (ListView) findViewById(R.id.list_view_persons);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_persons);

        listViewItemClick();
        addData();
    }

    public void addData(){
        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent Intent_Add_Person = new Intent(OpenAllPersonsActivity.this, AddEditPersonActivity.class);
                OpenAllPersonsActivity.this.startActivity(Intent_Add_Person);
            }
        });
    }





    private void popup(final long id) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        myDB.delete(Database.TABLE_PERSONS, id, Database.PERSON_ID);
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
        final ListView myList = (ListView) findViewById(R.id.list_view_persons);
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
                    Intent intent = new Intent(OpenAllPersonsActivity.this, AddEditPersonActivity.class);

                    Bundle b = new Bundle();
                    b.putBoolean("edit", true);
                    b.putLong("id", id);
                    intent.putExtras(b);

                    OpenAllPersonsActivity.this.startActivity(intent);
                }
                flag[0] = true;
            }
        });
    }


    private void refreshListView(){
        cursor = myDB.getAllRows(Database.TABLE_PERSONS, Database.ALL_KEYS_PERSONS, Database.PERSON_ID);
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.item_person_view, cursor, fromFieldsNames, toViewIDs, 0);
        personView.setAdapter(myCursorAdapter);
    }



}

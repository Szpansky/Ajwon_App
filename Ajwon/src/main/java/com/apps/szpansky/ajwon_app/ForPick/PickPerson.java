package com.apps.szpansky.ajwon_app.ForPick;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


import com.apps.szpansky.ajwon_app.AddEdit.AddEditPersonActivity;
import com.apps.szpansky.ajwon_app.R;
import com.apps.szpansky.ajwon_app.Tools.Database;
import com.apps.szpansky.ajwon_app.Tools.SimpleActivity;

/**
 * Created by Marcin on 2017-05-22.
 */

public class PickPerson extends SimpleActivity{

    @Override
    protected String[] setFromFieldsNames() {
        return Database.ALL_KEYS_PERSONS;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_simple_view;
    }

    @Override
    protected int getItemLayoutResourceId() {
        return (R.layout.item_person_view);
    }

    @Override
    protected Cursor setCursor() {return myDB.getAllRows(Database.TABLE_PERSONS, Database.ALL_KEYS_PERSONS, Database.PERSON_ID);}

    @Override
    protected String setTable() {
        return Database.TABLE_PERSONS;
    }

    @Override
    protected String[] setAllKeys() {
        return Database.ALL_KEYS_PERSONS;
    }

    @Override
    protected String setRowWhereId() {return Database.PERSON_ID;}

    @Override
    protected int[] setToViewIDs() {return (new int[]{R.id.personId, R.id.personName, R.id.personSurname, R.id.personPhone, R.id.personAddress});}

    @Override
    protected ListView setListView() {return ((ListView) findViewById(R.id.list_view_simple_view));}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button add = (Button) findViewById(R.id.add);
        add.setText("Add New Person");

        listViewItemClick();
        addData();
    }

    private void listViewItemClick() {

        final boolean[] flag = new boolean[1];
        flag[0] = true;

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                flag[0] = false;
                //popupForDelete(id);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (flag[0]) {

                    Intent intent = new Intent();
                    intent.putExtra("personId", (int)id );
                    setResult(RESULT_OK, intent);
                    finish();

                }
                flag[0] = true;
            }
        });


    }


    private void addData() {
        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PickPerson.this, AddEditPersonActivity.class);
                PickPerson.this.startActivity(intent);
            }
        });
    }



}

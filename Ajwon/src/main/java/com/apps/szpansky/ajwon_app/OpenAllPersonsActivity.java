package com.apps.szpansky.ajwon_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class OpenAllPersonsActivity extends OpenAllActivity {


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_open_persons;
    }


    @Override
    protected int getItemLayoutResourceId() {
        return (R.layout.item_person_view);
    }


    @Override
    public void setTable(String table) {
        this.table = Database.TABLE_PERSONS;
    }

    @Override
    public void setAllKeys(String[] allKeys) {
        this.allKeys = Database.ALL_KEYS_PERSONS;
    }

    @Override
    public void setRowWhereId(String rowWhereId) {
        this.rowWhereId = Database.PERSON_ID;
    }

    @Override
    public void setToViewIDs(int[] toViewIDs) {
        this.toViewIDs = new int[]{R.id.personId, R.id.personName, R.id.personSurname, R.id.personPhone, R.id.personAddress,};
    }

    @Override
    public void setListView(ListView listView) {
        this.listView = (ListView) findViewById(R.id.list_view_persons);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listViewItemClick();
        addData();
    }


    public void addData() {
        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent Intent_Add_Person = new Intent(OpenAllPersonsActivity.this, AddEditPersonActivity.class);
                OpenAllPersonsActivity.this.startActivity(Intent_Add_Person);
            }
        });
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
}
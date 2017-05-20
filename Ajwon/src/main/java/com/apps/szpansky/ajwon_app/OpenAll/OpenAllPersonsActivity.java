package com.apps.szpansky.ajwon_app.OpenAll;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.apps.szpansky.ajwon_app.AddEdit.AddEditPersonActivity;
import com.apps.szpansky.ajwon_app.Tools.Database;
import com.apps.szpansky.ajwon_app.R;

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
    protected void setTable(String table) {
        this.table = Database.TABLE_PERSONS;
    }

    @Override
    protected void setAllKeys(String[] allKeys) {
        this.allKeys = Database.ALL_KEYS_PERSONS;
    }

    @Override
    protected void setRowWhereId(String rowWhereId) {
        this.rowWhereId = Database.PERSON_ID;
    }

    @Override
    protected void setToViewIDs(int[] toViewIDs) {
        this.toViewIDs = new int[]{R.id.personId, R.id.personName, R.id.personSurname, R.id.personPhone, R.id.personAddress,};
    }

    @Override
    protected void setListView(ListView listView) {
        this.listView = (ListView) findViewById(R.id.list_view_persons);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listViewItemClick();
        addData();
    }


    private void addData() {
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
        final boolean[] flag = new boolean[1];
        flag[0] = true;

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                flag[0] = false;
                popup(id);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (flag[0]) {
                    Intent intent = new Intent(OpenAllPersonsActivity.this, AddEditPersonActivity.class);
                    toNextActivity.putBoolean("edit", true);
                    toNextActivity.putLong("id", id);
                    intent.putExtras(toNextActivity);
                    OpenAllPersonsActivity.this.startActivity(intent);
                }
                flag[0] = true;
            }
        });
    }
}
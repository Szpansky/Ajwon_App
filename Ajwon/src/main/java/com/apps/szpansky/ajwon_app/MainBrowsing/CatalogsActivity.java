package com.apps.szpansky.ajwon_app.MainBrowsing;

import android.content.Intent;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


import com.apps.szpansky.ajwon_app.Tools.SimpleActivity;
import com.apps.szpansky.ajwon_app.OpenAll.OpenAllWorksActivity;
import com.apps.szpansky.ajwon_app.R;
import com.apps.szpansky.ajwon_app.Tools.Database;

public class CatalogsActivity extends SimpleActivity {


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_simple_view;
    }

    @Override
    protected int getItemLayoutResourceId() {
        return (R.layout.item_work_view);
    }

    @Override
    protected Cursor setCursor() {return myDB.getAllRows(Database.TABLE_WORKS, Database.ALL_KEYS_WORK, Database.WORK_ID);}

    @Override
    protected String setTable() {
        return Database.TABLE_WORKS;
    }

    @Override
    protected String[] setAllKeys() {
        return Database.ALL_KEYS_WORK;
    }

    @Override
    protected String setRowWhereId() {
        return Database.WORK_ID;
    }

    @Override
    protected int[] setToViewIDs() {return (new int[]{R.id.workId, R.id.workDateStart, R.id.workDateEnd});}

    @Override
    protected ListView setListView() {return ((ListView) findViewById(R.id.list_view_simple_view));}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listViewItemClick();
        addData();

    }


    private void listViewItemClick() {
        final boolean[] flag = new boolean[1];
        flag[0] = true;

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                //TODO activity about information (delete option in new activity)

                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (flag[0]) {
                    Intent intent = new Intent(CatalogsActivity.this, ClientsActivity.class);
                    toNextActivity.putLong("catalogId", id);
                    intent.putExtras(toNextActivity);
                    CatalogsActivity.this.startActivity(intent);
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
                Intent intent = new Intent(CatalogsActivity.this, OpenAllWorksActivity.class);
                CatalogsActivity.this.startActivity(intent);
            }
        });
    }
}

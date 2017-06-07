package com.apps.szpansky.ajwon_app.MainBrowsing;

import android.content.Intent;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


import com.apps.szpansky.ajwon_app.AddEdit.AddEditWorkActivity;
import com.apps.szpansky.ajwon_app.SimpleData.Catalog;
import com.apps.szpansky.ajwon_app.Tools.SimpleActivity;
import com.apps.szpansky.ajwon_app.R;
import com.apps.szpansky.ajwon_app.Tools.Database;

public class CatalogsActivity extends SimpleActivity {

    Catalog catalog;


    @Override
    protected String[] setFromFieldsNames() {
        return Database.ALL_KEYS_CATALOG;
    }

    @Override
    protected int[] setToViewIDs() {
        return (new int[]{R.id.workId, R.id.workDateStart, R.id.workDateEnd});
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_simple_view;
    }

    @Override
    protected int getItemLayoutResourceId() {
        return (R.layout.item_work_view);
    }

    @Override
    protected Cursor setCursor() {
        return myDB.getAllRows(Database.TABLE_CATALOGS, Database.ALL_KEYS_CATALOG, Database.CATALOG_ID);
    }

    @Override
    protected String setTable() {
        return Database.TABLE_CATALOGS;
    }

    @Override
    protected String[] setAllKeys() {
        return Database.ALL_KEYS_CATALOG;
    }

    @Override
    protected String setRowWhereId() {
        return Database.CATALOG_ID;
    }



    @Override
    protected ListView setListView() {
        return ((ListView) findViewById(R.id.list_view_simple_view));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        catalog = new Catalog(myDB);

        Button add = (Button) findViewById(R.id.add);
        add.setText("Add New Catalog");
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

                popupForDelete(catalog, (int)id);


                //TODO activity about information (delete option in new activity)

                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (flag[0]) {
                    Intent intent = new Intent(CatalogsActivity.this, ClientsActivity.class);
                    toNextActivity.putLong("workId", id);
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
                Intent intent = new Intent(CatalogsActivity.this, AddEditWorkActivity.class);
                CatalogsActivity.this.startActivity(intent);
            }
        });
    }
}

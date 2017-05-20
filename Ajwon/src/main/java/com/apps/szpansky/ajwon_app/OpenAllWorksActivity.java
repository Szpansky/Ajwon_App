package com.apps.szpansky.ajwon_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class OpenAllWorksActivity extends OpenAllActivity {


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_open_works;
    }

    @Override
    protected int getItemLayoutResourceId() {
        return (R.layout.item_work_view);
    }

    @Override
    public void setTable(String table) {
        this.table = Database.TABLE_WORKS;
    }

    @Override
    public void setAllKeys(String[] allKeys) {
        this.allKeys = Database.ALL_KEYS_WORK;
    }

    @Override
    public void setRowWhereId(String rowWhereId) {
        this.rowWhereId = Database.WORK_CATALOG_NR;
    }

    @Override
    public void setToViewIDs(int[] toViewIDs) {
        this.toViewIDs = new int[]{R.id.workId, R.id.workDateStart, R.id.workDateEnd};
    }

    @Override
    public void setListView(ListView listView) {
        this.listView = (ListView) findViewById(R.id.list_view_catalogs);
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
                Intent Intent_Add_Work = new Intent(OpenAllWorksActivity.this, AddEditWorkActivity.class);
                OpenAllWorksActivity.this.startActivity(Intent_Add_Work);
            }
        });
    }


    private void listViewItemClick() {
        final ListView myList = (ListView) findViewById(R.id.list_view_catalogs);
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
                    Intent Intent_Open_Orders = new Intent(OpenAllWorksActivity.this, AddEditWorkActivity.class);
                    Bundle b = new Bundle();
                    b.putBoolean("edit", true);
                    b.putLong("id", id);
                    Intent_Open_Orders.putExtras(b);
                    OpenAllWorksActivity.this.startActivity(Intent_Open_Orders);
                }
                flag[0] = true;
            }
        });
    }


}

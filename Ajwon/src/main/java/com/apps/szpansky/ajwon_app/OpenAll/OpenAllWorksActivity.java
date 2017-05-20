package com.apps.szpansky.ajwon_app.OpenAll;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.apps.szpansky.ajwon_app.AddEdit.AddEditWorkActivity;
import com.apps.szpansky.ajwon_app.Tools.Database;
import com.apps.szpansky.ajwon_app.R;

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
    protected void setTable(String table) {
        this.table = Database.TABLE_WORKS;
    }

    @Override
    protected void setAllKeys(String[] allKeys) {
        this.allKeys = Database.ALL_KEYS_WORK;
    }

    @Override
    protected void setRowWhereId(String rowWhereId) {
        this.rowWhereId = Database.WORK_CATALOG_NR;
    }

    @Override
    protected void setToViewIDs(int[] toViewIDs) {
        this.toViewIDs = new int[]{R.id.workId, R.id.workDateStart, R.id.workDateEnd};
    }

    @Override
    protected void setListView(ListView listView) {
        this.listView = (ListView) findViewById(R.id.list_view_catalogs);
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
                Intent Intent_Add_Work = new Intent(OpenAllWorksActivity.this, AddEditWorkActivity.class);
                OpenAllWorksActivity.this.startActivity(Intent_Add_Work);
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
                    Intent Intent_Open_Orders = new Intent(OpenAllWorksActivity.this, AddEditWorkActivity.class);
                    toNextActivity.putBoolean("edit", true);
                    toNextActivity.putLong("id", id);
                    Intent_Open_Orders.putExtras(toNextActivity);
                    OpenAllWorksActivity.this.startActivity(Intent_Open_Orders);
                }
                flag[0] = true;
            }
        });
    }


}

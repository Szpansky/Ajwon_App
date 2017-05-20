package com.apps.szpansky.ajwon_app.OpenAll;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.apps.szpansky.ajwon_app.AddEdit.AddEditItemsActivity;
import com.apps.szpansky.ajwon_app.Tools.Database;
import com.apps.szpansky.ajwon_app.R;

public class OpenAllItemsActivity extends OpenAllActivity {


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_open_items;
    }


    @Override
    protected int getItemLayoutResourceId() {
        return (R.layout.item_item_view);
    }


    @Override
    protected void setTable(String table) {
        this.table = Database.TABLE_ITEMS;
    }

    @Override
    protected void setAllKeys(String[] allKeys) {
        this.allKeys = Database.ALL_KEYS_ITEMS;
    }

    @Override
    protected void setRowWhereId(String rowWhereId) {
        this.rowWhereId = Database.ITEM_CATALOG_NR;
    }

    @Override
    protected void setToViewIDs(int[] toViewIDs) {
        this.toViewIDs = new int[]{R.id.itemId, R.id.itemName, R.id.itemPrice, R.id.itemDiscount};
    }

    @Override
    protected void setListView(ListView listView) {
        this.listView = (ListView) findViewById(R.id.list_view_items);
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
                Intent Intent_Add_Item = new Intent(OpenAllItemsActivity.this, AddEditItemsActivity.class);
                OpenAllItemsActivity.this.startActivity(Intent_Add_Item);
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
                    Intent intent = new Intent(OpenAllItemsActivity.this, AddEditItemsActivity.class);
                    toNextActivity.putBoolean("edit", true);
                    toNextActivity.putLong("id", id);
                    intent.putExtras(toNextActivity);
                    OpenAllItemsActivity.this.startActivity(intent);
                }
                flag[0] = true;
            }
        });
    }
}

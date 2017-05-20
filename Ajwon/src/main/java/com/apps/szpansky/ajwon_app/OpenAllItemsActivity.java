package com.apps.szpansky.ajwon_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

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
    public void setTable(String table) {
        this.table = Database.TABLE_ITEMS;
    }

    @Override
    public void setAllKeys(String[] allKeys) {
        this.allKeys = Database.ALL_KEYS_ITEMS;
    }

    @Override
    public void setRowWhereId(String rowWhereId) {
        this.rowWhereId = Database.ITEM_CATALOG_NR;
    }

    @Override
    public void setToViewIDs(int[] toViewIDs) {
        this.toViewIDs = new int[]{R.id.itemId, R.id.itemName, R.id.itemPrice, R.id.itemDiscount};
    }

    @Override
    public void setListView(ListView listView) {
        this.listView = (ListView) findViewById(R.id.list_view_items);
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
                Intent Intent_Add_Item = new Intent(OpenAllItemsActivity.this, AddEditItemsActivity.class);
                OpenAllItemsActivity.this.startActivity(Intent_Add_Item);
            }
        });
    }


    private void listViewItemClick() {
        final ListView myList = (ListView) findViewById(R.id.list_view_items);
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
                    Intent intent = new Intent(OpenAllItemsActivity.this, AddEditItemsActivity.class);

                    Bundle b = new Bundle();
                    b.putBoolean("edit", true);
                    b.putLong("id", id);
                    intent.putExtras(b);

                    OpenAllItemsActivity.this.startActivity(intent);
                }
                flag[0] = true;
            }
        });
    }
}

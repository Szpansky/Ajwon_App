package com.apps.szpansky.ajwon_app.for_pick;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


import com.apps.szpansky.ajwon_app.add_edit.AddEditItemsActivity;
import com.apps.szpansky.ajwon_app.R;
import com.apps.szpansky.ajwon_app.simple_data.Item;
import com.apps.szpansky.ajwon_app.tools.SimpleActivity;


public class PickItem extends SimpleActivity {

    FloatingActionButton add;

    public PickItem() {
        super(new Item());
    }


    @Override
    protected ListView setListView() {
        return ((ListView) findViewById(R.id.list_view_simple_view));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        add = (FloatingActionButton) findViewById(R.id.add);

        addData();
        listViewItemClick();
    }


    private void addData() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PickItem.this, AddEditItemsActivity.class);
                PickItem.this.startActivity(intent);
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

                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (flag[0]) {

                    Intent intent = new Intent();
                    intent.putExtra("itemId", (int) id);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                flag[0] = true;
            }
        });
    }
}

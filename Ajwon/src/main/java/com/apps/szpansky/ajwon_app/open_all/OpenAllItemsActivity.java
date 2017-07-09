package com.apps.szpansky.ajwon_app.open_all;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.apps.szpansky.ajwon_app.add_edit.AddEditItemsActivity;
import com.apps.szpansky.ajwon_app.simple_data.Item;
import com.apps.szpansky.ajwon_app.tools.SimpleActivity;

public class OpenAllItemsActivity extends SimpleActivity {


    public OpenAllItemsActivity() {
        super(new Item());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listViewItemClick();
    }


    @Override
    protected void addButtonClick() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OpenAllItemsActivity.this, AddEditItemsActivity.class);
                OpenAllItemsActivity.this.startActivity(intent);
            }
        });
    }


    public void listViewItemClick() {
        final boolean[] flag = new boolean[1];
        flag[0] = true;

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                flag[0] = false;
                popupForDelete((int) id);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (flag[0]) {
                    Intent intent = new Intent(OpenAllItemsActivity.this, AddEditItemsActivity.class);

                    toNextActivityBundle.putBoolean("isEdit", true);
                    toNextActivityBundle.putInt("itemId", (int) id);

                    intent.putExtras(toNextActivityBundle);
                    OpenAllItemsActivity.this.startActivity(intent);
                }
                flag[0] = true;
            }
        });
    }
}

package com.apps.szpansky.ajwon_app.OpenAll;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.apps.szpansky.ajwon_app.AddEdit.AddEditItemsActivity;
import com.apps.szpansky.ajwon_app.SimpleData.Item;
import com.apps.szpansky.ajwon_app.Tools.Data;
import com.apps.szpansky.ajwon_app.Tools.Database;
import com.apps.szpansky.ajwon_app.R;
import com.apps.szpansky.ajwon_app.Tools.SimpleActivity;

public class OpenAllItemsActivity extends SimpleActivity {

    //Item item;

    public OpenAllItemsActivity() {
        super(new Item());
    }


    @Override
    protected ListView setListView() {return ((ListView) findViewById(R.id.list_view_simple_view));}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //item = new Item(myDB);

        Button add = (Button) findViewById(R.id.add);
        add.setText("Add New Item");
        listViewItemClick();
        addData();
    }


    private void addData() {
        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
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
                popupForDelete((int)id);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (flag[0]) {

                    Intent intent = new Intent(OpenAllItemsActivity.this, AddEditItemsActivity.class);

                    Item.clickedItemId = (int)id;

                    toNextActivity.putBoolean("edit", true);
                    toNextActivity.putInt("id", (int)id);



                    intent.putExtras(toNextActivity);
                    OpenAllItemsActivity.this.startActivity(intent);

                }
                flag[0] = true;
            }
        });
    }
}

package com.apps.szpansky.ajwon_app.MainBrowsing;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


import com.apps.szpansky.ajwon_app.AddEdit.AddEditCatalogActivity;
import com.apps.szpansky.ajwon_app.SimpleData.Catalog;
import com.apps.szpansky.ajwon_app.SimpleData.Client;
import com.apps.szpansky.ajwon_app.Tools.SimpleActivity;
import com.apps.szpansky.ajwon_app.R;

public class CatalogsActivity extends SimpleActivity {

    //Catalog catalog;

    public CatalogsActivity() {
        super(new Catalog());
    }


    @Override
    protected ListView setListView() {
        return ((ListView) findViewById(R.id.list_view_simple_view));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //catalog = new Catalog(myDB);

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

                popupForDelete((int)id);


                //TODO activity about information (delete option in new activity)

                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (flag[0]) {
                    Intent intent = new Intent(CatalogsActivity.this, ClientsActivity.class);
                    toNextActivity.putInt("catalogId", (int)id);
                    intent.putExtras(toNextActivity);

                    Client.clickedCatalogId =(int)id;

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
                Intent intent = new Intent(CatalogsActivity.this, AddEditCatalogActivity.class);
                CatalogsActivity.this.startActivity(intent);
            }
        });
    }
}

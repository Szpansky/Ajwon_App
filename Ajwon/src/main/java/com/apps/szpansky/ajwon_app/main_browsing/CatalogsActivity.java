package com.apps.szpansky.ajwon_app.main_browsing;

import android.content.Intent;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import com.apps.szpansky.ajwon_app.add_edit.AddEditCatalogActivity;
import com.apps.szpansky.ajwon_app.simple_data.Catalog;
import com.apps.szpansky.ajwon_app.simple_data.Client;
import com.apps.szpansky.ajwon_app.tools.SimpleActivity;
import com.apps.szpansky.ajwon_app.R;


public class CatalogsActivity extends SimpleActivity {

    FloatingActionButton add;

    public CatalogsActivity() {
        super(new Catalog());
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
                Intent intent = new Intent(CatalogsActivity.this, AddEditCatalogActivity.class);
                CatalogsActivity.this.startActivity(intent);
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
                    Client.clickedCatalogId =(int)id;
                    CatalogsActivity.this.startActivity(intent);
                }
                flag[0] = true;
            }
        });
    }
}

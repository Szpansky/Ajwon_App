package com.apps.szpansky.ajwon_app.open_all;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.apps.szpansky.ajwon_app.add_edit.AddEditCatalogActivity;
import com.apps.szpansky.ajwon_app.simple_data.Catalog;
import com.apps.szpansky.ajwon_app.R;
import com.apps.szpansky.ajwon_app.tools.SimpleActivity;

public class OpenAllCatalogsActivity extends SimpleActivity {

    FloatingActionButton add;


    public OpenAllCatalogsActivity() {
        super(new Catalog());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        add = (FloatingActionButton) findViewById(R.id.add);

        listViewItemClick();
        addData();
    }


    private void addData() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OpenAllCatalogsActivity.this, AddEditCatalogActivity.class);
                OpenAllCatalogsActivity.this.startActivity(intent);
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
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (flag[0]) {
                    Intent intent = new Intent(OpenAllCatalogsActivity.this, AddEditCatalogActivity.class);

                    toNextActivityBundle.putInt("catalogId", (int)id);
                    toNextActivityBundle.putBoolean("isEdit", true);

                    intent.putExtras(toNextActivityBundle);
                    OpenAllCatalogsActivity.this.startActivity(intent);
                }
                flag[0] = true;
            }
        });
    }
}

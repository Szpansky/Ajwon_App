package com.apps.szpansky.ajwon_app.MainBrowsing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.apps.szpansky.ajwon_app.ForPick.PickPerson;
import com.apps.szpansky.ajwon_app.R;
import com.apps.szpansky.ajwon_app.SimpleData.Client;
import com.apps.szpansky.ajwon_app.SimpleData.Order;
import com.apps.szpansky.ajwon_app.Tools.SimpleActivity;


public class ClientsActivity extends SimpleActivity {

    Button add;

    public ClientsActivity() {
        super(new Client());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        add = (Button) findViewById(R.id.add);
        add.setText("Pick Person");

        addData();
        listViewItemClick();
    }


    private void addData() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientsActivity.this, PickPerson.class);
                startActivityForResult(intent, 1);
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
                //TODO delete in new activiti with informations
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (flag[0]) {

                    Intent intent = new Intent(ClientsActivity.this, OrdersActivity.class);
                    Order.clickedClientId = (int) id;
                    ClientsActivity.this.startActivity(intent);
                }
                flag[0] = true;
            }
        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {

                Integer personId = data.getIntExtra("personId" , 0);
                Integer catalogId = Client.clickedCatalogId;

                myDB.insertDataToClients(catalogId.toString(), personId.toString(), "Waiting");

                refreshListView();
            }
        }
    }
}

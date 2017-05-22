package com.apps.szpansky.ajwon_app.MainBrowsing;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.apps.szpansky.ajwon_app.ForPick.PickPerson;
import com.apps.szpansky.ajwon_app.R;
import com.apps.szpansky.ajwon_app.Tools.Database;
import com.apps.szpansky.ajwon_app.Tools.SimpleActivity;



public class ClientsActivity extends SimpleActivity {
    Bundle b = new Bundle();


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_simple_view;
    }

    @Override
    protected int getItemLayoutResourceId() {
        return R.layout.item_client_view;
    }

    @Override
    protected Cursor setCursor() {
        return myDB.getAllRows(Database.TABLE_CLIENTS,Database.ALL_KEYS_CLIENTS,Database.CLIENT_WORK_ID, b.getLong("workId"));
    }

    @Override
    protected String setTable() {
        return Database.TABLE_CLIENTS;
    }

    @Override
    protected String[] setAllKeys() {
        return Database.ALL_KEYS_CLIENTS;
    }

    @Override
    protected String setRowWhereId() {
        return Database.CLIENT_ID;
    }

    @Override
    protected int[] setToViewIDs() {
        return (new int[]{R.id.orderId, R.id.workId, R.id.personId, R.id.clientDate});
    }

    @Override
    protected ListView setListView() {
        return ((ListView) findViewById(R.id.list_view_simple_view));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = getIntent().getExtras();
        addData();
        listViewItemClick();
    }

    private void addData() {
        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(ClientsActivity.this, PickPerson.class);
                startActivityForResult(i, 1);


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
                //popup(id);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (flag[0]) {

                    Intent intent = new Intent(ClientsActivity.this, OrdersActivity.class);
                    toNextActivity.putLong("orderId", id);
                    intent.putExtras(toNextActivity);
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

                Long personId = data.getLongExtra("personId" , 0);
                Long test = b.getLong("workId");

                myDB.insertDataToClients(test.toString(), personId.toString());
                refreshListView();


            }
        }
    }


}

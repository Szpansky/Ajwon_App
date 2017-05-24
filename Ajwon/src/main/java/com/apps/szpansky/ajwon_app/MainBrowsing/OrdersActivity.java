package com.apps.szpansky.ajwon_app.MainBrowsing;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.apps.szpansky.ajwon_app.ForPick.PickItem;
import com.apps.szpansky.ajwon_app.ForPick.PickPerson;
import com.apps.szpansky.ajwon_app.R;
import com.apps.szpansky.ajwon_app.Tools.Database;
import com.apps.szpansky.ajwon_app.Tools.SimpleActivity;



public class OrdersActivity extends SimpleActivity {
    Bundle b = new Bundle();


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_simple_view;
    }

    @Override
    protected int getItemLayoutResourceId() {
        return (R.layout.item_order_view);
    }


    @Override
    protected Cursor setCursor() {
        return myDB.getAllRows(Database.TABLE_ORDERS,Database.ALL_KEYS_ORDERS,Database.ORDER_CLIENT_ID, b.getLong("clientId"));
    }


    @Override
    protected String setTable() {
        return Database.TABLE_ORDERS;
    }

    @Override
    protected String[] setAllKeys() {
        return Database.ALL_KEYS_ORDERS;
    }

    @Override
    protected String setRowWhereId() {
        return Database.ORDER_ID;
    }

    @Override
    protected int[] setToViewIDs() {return (new int[]{R.id.orderId, R.id.personId, R.id.itemId, R.id.orderAmount, R.id.orderStatus});}

    @Override
    protected ListView setListView() {return ((ListView) findViewById(R.id.list_view_simple_view));}

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
                Intent i = new Intent(OrdersActivity.this, PickItem.class);
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

                }
                flag[0] = true;
            }
        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {

                Long itemId = data.getLongExtra("itemId" , 0);
                Long test = b.getLong("clientId");
                myDB.insertDataToOrders(test.toString(),itemId.toString(),"12","Czekam");
                //myDB.insertDataToClients(test.toString(), personId.toString());
                refreshListView();


            }
        }
    }


}

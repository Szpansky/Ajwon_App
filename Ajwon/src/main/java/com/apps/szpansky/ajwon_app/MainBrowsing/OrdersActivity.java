package com.apps.szpansky.ajwon_app.MainBrowsing;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.apps.szpansky.ajwon_app.ForPick.PickItem;
import com.apps.szpansky.ajwon_app.R;
import com.apps.szpansky.ajwon_app.SimpleData.Order;
import com.apps.szpansky.ajwon_app.Tools.Database;
import com.apps.szpansky.ajwon_app.Tools.SimpleActivity;


public class OrdersActivity extends SimpleActivity {
    Bundle b = new Bundle();
    Order order;

    @Override
    protected String[] setFromFieldsNames() {
        return new String[]{Database.ORDER_ID, Database.ORDER_CLIENT_ID, Database.ORDER_ITEM_ID, Database.ORDER_AMOUNT, Database.ORDER_TOTAL, Database.ITEM_NAME};
    }

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
        //return order.getCursor();
        return myDB.getItems(b.getInt("clientId"));
        //return myDB.getAllRows(Database.TABLE_ORDERS,Database.ALL_KEYS_ORDERS,Database.ORDER_CLIENT_ID, b.getLong("clientId"));
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
    protected int[] setToViewIDs() {
        return (new int[]{R.id.orderId, R.id.orderPersonId, R.id.orderItemId, R.id.orderAmount, R.id.orderTotal, R.id.orderItemName});
    }

    @Override
    protected ListView setListView() {
        return ((ListView) findViewById(R.id.list_view_simple_view));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        order = new Order(myDB);

        Button add = (Button) findViewById(R.id.add);
        add.setText("Pick Item");
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

                popupForDelete(order, (int)id );

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
            if (resultCode == RESULT_OK) {

                Integer itemId = data.getIntExtra("itemId", 0);
                Integer clientId = b.getInt("clientId");

                boolean isInserted = myDB.updateRowOrder(clientId, itemId, 1);
                if (!isInserted) myDB.insertDataToOrders(clientId.toString(), itemId.toString(), 1);


                refreshListView();


            }
        }
    }


}

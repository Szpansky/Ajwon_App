package com.apps.szpansky.ajwon_app.main_browsing;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;

import com.apps.szpansky.ajwon_app.for_pick.PickItem;
import com.apps.szpansky.ajwon_app.R;
import com.apps.szpansky.ajwon_app.simple_data.Order;
import com.apps.szpansky.ajwon_app.tools.SimpleActivity;


public class OrdersActivity extends SimpleActivity {

    FloatingActionButton add;

    public OrdersActivity() {
        super(new Order());
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
                Intent intent = new Intent(OrdersActivity.this, PickItem.class);
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

                popupForDelete((int)id );
                //TODO delete in new activiti with informations
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
                Integer clientId = Order.clickedClientId;

                int count = 1;

                boolean isInserted = myDB.updateRowOrder(clientId, itemId, count);
                if (!isInserted) myDB.insertDataToOrders(clientId.toString(), itemId.toString(), count);

                refreshListView();
            }
        }
    }
}

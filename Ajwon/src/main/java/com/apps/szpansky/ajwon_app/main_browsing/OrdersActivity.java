package com.apps.szpansky.ajwon_app.main_browsing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.apps.szpansky.ajwon_app.for_pick.PickItem;
import com.apps.szpansky.ajwon_app.simple_data.Order;
import com.apps.szpansky.ajwon_app.tools.SimpleActivity;


public class OrdersActivity extends SimpleActivity {

    private final int BACK_CODE = 1;

    public OrdersActivity() {
        super(new Order());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listViewItemClick();
    }


    @Override
    protected void onAddButtonClick() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrdersActivity.this, PickItem.class);
                startActivityForResult(intent, BACK_CODE);
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

                popupForDelete((int) id);
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
        if (requestCode == BACK_CODE) {
            if (resultCode == RESULT_OK) {

                Integer itemId = data.getIntExtra("itemId", 0);
                Integer clientId = Order.clickedClientId;

                int count = 1;

                boolean isInserted = myDB.updateRowOrder(clientId, itemId, count);
                if (!isInserted)
                    myDB.insertDataToOrders(clientId.toString(), itemId.toString(), count);

                refreshListView();
            }
        }
    }
}

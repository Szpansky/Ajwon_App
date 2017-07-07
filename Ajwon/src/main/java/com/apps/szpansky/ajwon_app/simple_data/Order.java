package com.apps.szpansky.ajwon_app.simple_data;

import android.database.Cursor;

import com.apps.szpansky.ajwon_app.R;
import com.apps.szpansky.ajwon_app.tools.Data;
import com.apps.szpansky.ajwon_app.tools.Database;



public class Order extends Data{

    public static int clickedClientId;


    @Override
    public int getItemLayoutResourceId(){
        return (R.layout.item_order_view);
    }


    @Override
    public Cursor setCursor(Database myDB){
        return myDB.getOrders(clickedClientId, this.filter);
    }


    @Override
    public int[] getToViewIDs(){

        return (new int[]{
                R.id.orderItemId,
                R.id.orderItemName,
                R.id.orderAmount,
                R.id.orderTotal
        });
    }


    @Override
    public String[] getFromFieldsNames(){

        return new String[]{
                Database.ORDER_ITEM_ID,
                Database.ITEM_NAME,
                Database.ORDER_AMOUNT,
                Database.ORDER_TOTAL
        };
    }


    @Override
    public void deleteData(int orderId, Database myDB) {

        myDB.delete(Database.TABLE_ORDERS,Database.ORDER_ID,orderId);
    }
}

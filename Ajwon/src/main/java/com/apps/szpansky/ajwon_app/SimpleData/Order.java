package com.apps.szpansky.ajwon_app.SimpleData;

import android.database.Cursor;

import com.apps.szpansky.ajwon_app.R;
import com.apps.szpansky.ajwon_app.Tools.Data;
import com.apps.szpansky.ajwon_app.Tools.Database;



public class Order extends Data{

    public static int clickedClientId;


    @Override
    public int getItemLayoutResourceId(){
        return (R.layout.item_order_view);
    }


    @Override
    public Cursor setCursor(Database myDB){
        return myDB.getOrders(clickedClientId);
    }


    @Override
    public int[] getToViewIDs(){

        return (new int[]{
                R.id.orderId,
                R.id.orderPersonId,
                R.id.orderItemId,
                R.id.orderAmount,
                R.id.orderTotal,
                R.id.orderItemName});
    }


    @Override
    public String[] getFromFieldsNames(){

        return new String[]{
                Database.ORDER_ID,
                Database.ORDER_CLIENT_ID,
                Database.ORDER_ITEM_ID,
                Database.ORDER_AMOUNT,
                Database.ORDER_TOTAL,
                Database.ITEM_NAME};
    }


    @Override
    public void deleteData(int orderId, Database myDB) {

        myDB.delete(Database.TABLE_ORDERS,Database.ORDER_ID,orderId);
    }
}

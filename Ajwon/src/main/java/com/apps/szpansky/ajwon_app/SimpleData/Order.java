package com.apps.szpansky.ajwon_app.SimpleData;

import android.database.Cursor;

import com.apps.szpansky.ajwon_app.R;
import com.apps.szpansky.ajwon_app.Tools.Data;
import com.apps.szpansky.ajwon_app.Tools.Database;



public class Order extends Data{

    public static int clickedClientId;           //TODO get id from b.getInt("clickedClientId"))



    @Override
    public int getLayoutResourceId(){

        return R.layout.activity_simple_view;
    }

    @Override
    public int getItemLayoutResourceId(){

        return (R.layout.item_order_view);
    }

    @Override
    public Cursor setCursor(Database myDB){

        return myDB.getClientItems(clickedClientId);
    }

    @Override
    public String setTable(){

        return Database.TABLE_ORDERS;
    }

    @Override
    public String[] setAllKeys(){

        return Database.ALL_KEYS_ORDERS;
    }

    @Override
    public String setRowWhereId(){

        return Database.ORDER_ID;
    }

    @Override
    public int[] setToViewIDs(){

        return (new int[]{R.id.orderId, R.id.orderPersonId, R.id.orderItemId, R.id.orderAmount, R.id.orderTotal, R.id.orderItemName});
    }

    @Override
    public String[] setFromFieldsNames(){

        return new String[]{Database.ORDER_ID, Database.ORDER_CLIENT_ID, Database.ORDER_ITEM_ID, Database.ORDER_AMOUNT, Database.ORDER_TOTAL, Database.ITEM_NAME};
    }



    @Override
    public void deleteData(int orderId, Database myDB) {
        myDB.delete(Database.TABLE_ORDERS,Database.ORDER_ID,orderId);
    }


}

package com.apps.szpansky.ajwon_app.SimpleData;

import android.database.Cursor;

import com.apps.szpansky.ajwon_app.Tools.Data;
import com.apps.szpansky.ajwon_app.Tools.Database;



public class Order extends Data{


    public Order(Database myDB) {
        super(myDB);
    }

    @Override
    public void deleteData(int orderId) {
        myDB.delete(Database.TABLE_ORDERS,Database.ORDER_ID,orderId);
    }


}

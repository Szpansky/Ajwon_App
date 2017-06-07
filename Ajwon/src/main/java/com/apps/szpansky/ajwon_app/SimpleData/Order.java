package com.apps.szpansky.ajwon_app.SimpleData;

import com.apps.szpansky.ajwon_app.Tools.Database;

/**
 * Created by Marcin on 2017-06-06.
 */

public class Order extends Data{


    public Order(Database myDB) {
        super(myDB);
    }

    @Override
    public void deleteData(int orderId) {
        myDB.delete(Database.TABLE_ORDERS,Database.ORDER_ID,orderId);
    }


}

package com.apps.szpansky.ajwon_app.SimpleData;

import com.apps.szpansky.ajwon_app.Tools.Database;


public class Item extends Order{

    public Item(Database myDB) {
        super(myDB);
    }

    @Override
    public void deleteData(int itemId) {
        myDB.delete(Database.TABLE_ORDERS,Database.ORDER_ITEM_ID,itemId);
        myDB.delete(Database.TABLE_ITEMS, Database.ITEM_ID, itemId);
    }


}

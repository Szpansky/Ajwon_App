package com.apps.szpansky.ajwon_app.SimpleData;

import android.database.Cursor;
import android.widget.ListView;

import com.apps.szpansky.ajwon_app.R;
import com.apps.szpansky.ajwon_app.Tools.Database;


public class Item extends Order{

    public static int clickedItemId;


    @Override
    public int getItemLayoutResourceId(){

        return (R.layout.item_item_view);
    }


    @Override
    public Cursor setCursor(Database myDB){

        return myDB.getItems();
    }


    @Override
    public int[] getToViewIDs(){

        return (new int[]{
                R.id.itemId,
                R.id.itemName,
                R.id.itemPrice,
                R.id.itemDiscount});
    }


    @Override
    public String[] getFromFieldsNames(){

        return new String[]{
                Database.ITEM_ID,
                Database.ITEM_NAME,
                Database.ITEM_PRICE,
                Database.ITEM_DISCOUNT};
    }


    @Override
    public void deleteData(int itemId, Database myDB) {

        myDB.delete(Database.TABLE_ORDERS,Database.ORDER_ITEM_ID,itemId);
        myDB.delete(Database.TABLE_ITEMS, Database.ITEM_ID, itemId);
    }
}

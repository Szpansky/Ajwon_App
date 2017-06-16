package com.apps.szpansky.ajwon_app.SimpleData;

import android.database.Cursor;

import com.apps.szpansky.ajwon_app.R;
import com.apps.szpansky.ajwon_app.Tools.Database;


public class Client extends Order {

    public static int clickedCatalogId;


    @Override
    public int getLayoutResourceId(){

        return R.layout.activity_simple_view;
    }


    @Override
    public int getItemLayoutResourceId(){

        return R.layout.item_client_view;
    }


    @Override
    public Cursor setCursor(Database myDB){
        return myDB.getClients(clickedCatalogId);
    }


    @Override
    public String setTable(){

        return Database.TABLE_CLIENTS;
    }


    @Override
    public String[] setAllKeys(){

        return Database.ALL_KEYS_CLIENTS;
    }


    @Override
    public String setRowWhereId(){

        return Database.CLIENT_ID;
    }


    @Override
    public int[] setToViewIDs(){

        return (new int[]{R.id.clientOrderId, R.id.clientCatalogId, R.id.clientPersonId, R.id.clientDate, R.id.clientName, R.id.clientSurname});
    }


    @Override
    public String[] setFromFieldsNames(){

        return  new String[]{Database.CLIENT_ID, Database.CLIENT_CATALOG_ID, Database.CLIENT_PERSON_ID, Database.CLIENT_DATE, Database.PERSON_NAME, Database.PERSON_SURNAME};
    }


    @Override
    public void deleteData(int clientId, Database myDB) {

        myDB.delete(Database.TABLE_ORDERS, Database.ORDER_CLIENT_ID, clientId);
        myDB.delete(Database.TABLE_CLIENTS, Database.CLIENT_ID, clientId);
    }
}

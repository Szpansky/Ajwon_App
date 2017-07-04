package com.apps.szpansky.ajwon_app.SimpleData;

import android.database.Cursor;

import com.apps.szpansky.ajwon_app.R;
import com.apps.szpansky.ajwon_app.Tools.Database;


public class Client extends Order {

    public static int clickedCatalogId;


    @Override
    public int getItemLayoutResourceId(){

        return R.layout.item_client_view;
    }


    @Override
    public Cursor setCursor(Database myDB){
        return myDB.getClients(clickedCatalogId);
    }


    @Override
    public int[] getToViewIDs(){

        return (new int[]{
               // R.id.clientOrderId,
               // R.id.clientCatalogId,
               // R.id.clientPersonId,
                R.id.clientDate,
                R.id.clientName,
                R.id.clientSurname,
                R.id.clientOrderStatus
               // R.id.clientAmount
        });
    }


    @Override
    public String[] getFromFieldsNames(){

        return  new String[]{
                //Database.CLIENT_ID,
               // Database.CLIENT_CATALOG_ID,
              //  Database.CLIENT_PERSON_ID,
                Database.CLIENT_DATE,
                Database.PERSON_NAME,
                Database.PERSON_SURNAME,
                Database.CLIENT_STATUS
                //Database.ORDER_AMOUNT
        };
    }


    @Override
    public void deleteData(int clientId, Database myDB) {

        myDB.delete(Database.TABLE_ORDERS, Database.ORDER_CLIENT_ID, clientId);
        myDB.delete(Database.TABLE_CLIENTS, Database.CLIENT_ID, clientId);
    }
}

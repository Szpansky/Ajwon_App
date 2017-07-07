package com.apps.szpansky.ajwon_app.simple_data;

import android.database.Cursor;

import com.apps.szpansky.ajwon_app.R;
import com.apps.szpansky.ajwon_app.tools.Database;


public class Client extends Order {

    public static int clickedCatalogId;


    @Override
    public int getItemLayoutResourceId(){

        return R.layout.item_client_view;
    }


    @Override
    public Cursor setCursor(Database myDB){
        return myDB.getClients(clickedCatalogId, this.filter);
    }


    @Override
    public int[] getToViewIDs(){

        return (new int[]{
                R.id.clientName,
                R.id.clientSurname,
                R.id.clientDate,
                R.id.clientOrderStatus
        });
    }


    @Override
    public String[] getFromFieldsNames(){

        return  new String[]{
                Database.PERSON_NAME,
                Database.PERSON_SURNAME,
                Database.CLIENT_DATE,
                Database.CLIENT_STATUS
        };
    }


    @Override
    public void deleteData(int clientId, Database myDB) {

        myDB.delete(Database.TABLE_ORDERS, Database.ORDER_CLIENT_ID, clientId);
        myDB.delete(Database.TABLE_CLIENTS, Database.CLIENT_ID, clientId);
    }
}

package com.apps.szpansky.ajwon_app.SimpleData;

import android.database.Cursor;
import android.widget.ListView;

import com.apps.szpansky.ajwon_app.R;
import com.apps.szpansky.ajwon_app.Tools.Database;



public class Catalog extends Client {

    public static int clickedCatalogId;

    @Override
    public int getLayoutResourceId(){

        return R.layout.activity_simple_view;
    }

    @Override
    public int getItemLayoutResourceId(){

        return (R.layout.item_work_view);
    }

    @Override
    public Cursor setCursor(Database myDB){

        return myDB.getAllRows(Database.TABLE_CATALOGS, Database.ALL_KEYS_CATALOG, Database.CATALOG_ID);
    }

    @Override
    public String setTable(){

        return Database.TABLE_CATALOGS;
    }

    @Override
    public String[] setAllKeys(){

        return Database.ALL_KEYS_CATALOG;
    }

    @Override
    public String setRowWhereId(){

        return Database.CATALOG_ID;
    }

    @Override
    public int[] setToViewIDs(){

        return (new int[]{R.id.workId, R.id.workDateStart, R.id.workDateEnd});
    }

    @Override
    public String[] setFromFieldsNames(){

        return Database.ALL_KEYS_CATALOG;
    }


    @Override
    public void deleteData(int catalogId, Database myDB) {

        int clientId;


        do {
            clientId = myDB.getInt(Database.TABLE_CLIENTS, Database.CLIENT_ID, Database.CLIENT_CATALOG_ID, catalogId);
            if (clientId != -1) super.deleteData(clientId, myDB);
        } while (clientId != -1);


        myDB.delete(Database.TABLE_CATALOGS, Database.CATALOG_ID, catalogId);
        //myDB.delete(Database.TABLE_CLIENTS,Database.CLIENT_CATALOG_ID,clickedCatalogId);


    }

}

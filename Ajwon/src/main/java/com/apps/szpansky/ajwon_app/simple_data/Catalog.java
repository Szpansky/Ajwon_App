package com.apps.szpansky.ajwon_app.simple_data;

import android.database.Cursor;

import com.apps.szpansky.ajwon_app.R;
import com.apps.szpansky.ajwon_app.tools.Database;



public class Catalog extends Client {

    public static int clickedCatalogId;


    @Override
    public int getItemLayoutResourceId(){

        return (R.layout.item_catalog_view);
    }


    @Override
    public Cursor setCursor(Database myDB){

        return myDB.getCatalogs(this.filter);
    }


    @Override
    public int[] getToViewIDs(){

        return (new int[]{
                //R.id.catalogId,
                R.id.catalogNumber,
                R.id.catalogDateStart,
                R.id.catalogDateEnd
        });
    }


    @Override
    public String[] getFromFieldsNames(){

        return new String[]{
                //Database.CATALOG_ID,
                Database.CATALOG_NUMBER,
                Database.CATALOG_DATE_START,
                Database.CATALOG_DATE_ENDS};
    }


    @Override
    public void deleteData(int catalogId, Database myDB) {

        int clientId;

        do {
            clientId = myDB.getInt(Database.TABLE_CLIENTS, Database.CLIENT_ID, Database.CLIENT_CATALOG_ID, catalogId);
            if (clientId != -1) super.deleteData(clientId, myDB);
        } while (clientId != -1);

        myDB.delete(Database.TABLE_CATALOGS, Database.CATALOG_ID, catalogId);
    }
}

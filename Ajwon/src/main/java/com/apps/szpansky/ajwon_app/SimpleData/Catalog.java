package com.apps.szpansky.ajwon_app.SimpleData;

import com.apps.szpansky.ajwon_app.Tools.Database;



public class Catalog extends Client {

    public Catalog(Database myDB) {
        super(myDB);

    }

    @Override
    public void deleteData(int catalogId) {

        int clientId;


        do {
            clientId = myDB.getInt(Database.TABLE_CLIENTS, Database.CLIENT_ID, Database.CLIENT_CATALOG_ID, catalogId);
            if (clientId != -1) super.deleteData(clientId);
        } while (clientId != -1);


        myDB.delete(Database.TABLE_CATALOGS, Database.CATALOG_ID, catalogId);
        //myDB.delete(Database.TABLE_CLIENTS,Database.CLIENT_CATALOG_ID,catalogId);


    }

}

package com.apps.szpansky.ajwon_app.SimpleData;

import com.apps.szpansky.ajwon_app.Tools.Database;


public class Client extends Order {

    public Client(Database myDB) {
        super(myDB);
    }

    @Override
    public void deleteData(int clientId) {
        myDB.delete(Database.TABLE_ORDERS, Database.ORDER_CLIENT_ID, clientId);
        myDB.delete(Database.TABLE_CLIENTS, Database.CLIENT_ID, clientId);
    }
}

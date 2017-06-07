package com.apps.szpansky.ajwon_app.SimpleData;

import com.apps.szpansky.ajwon_app.Tools.Database;


public class Person extends Client {

    public Person(Database myDB) {
        super(myDB);
    }

    @Override
    public void deleteData(int personId) {


        int clientId;


        do {
            clientId = myDB.getInt(Database.TABLE_CLIENTS, Database.CLIENT_ID, Database.CLIENT_PERSON_ID, personId);
            if (clientId != -1) super.deleteData(clientId);
        } while (clientId != -1);


        myDB.delete(Database.TABLE_PERSONS, Database.PERSON_ID, personId);


    }
}

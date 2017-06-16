package com.apps.szpansky.ajwon_app.SimpleData;

import android.database.Cursor;
import android.widget.ListView;

import com.apps.szpansky.ajwon_app.R;
import com.apps.szpansky.ajwon_app.Tools.Database;


public class Person extends Client {

    public static int clickedPersonId;


    @Override
    public int getLayoutResourceId(){

        return R.layout.activity_simple_view;
    }


    @Override
    public int getItemLayoutResourceId(){

        return (R.layout.item_person_view);
    }


    @Override
    public Cursor setCursor(Database myDB){

        return myDB.getAllRows(Database.TABLE_PERSONS, Database.ALL_KEYS_PERSONS, Database.PERSON_ID);
    }


    @Override
    public String setTable(){

        return Database.TABLE_PERSONS;
    }


    @Override
    public String[] setAllKeys(){

        return Database.ALL_KEYS_PERSONS;
    }


    @Override
    public String setRowWhereId(){

        return Database.PERSON_ID;
    }


    @Override
    public int[] setToViewIDs(){

        return (new int[]{R.id.personId, R.id.personName, R.id.personSurname, R.id.personPhone, R.id.personAddress});
    }


    @Override
    public String[] setFromFieldsNames(){

        return Database.ALL_KEYS_PERSONS;
    }


    @Override
    public void deleteData(int personId, Database myDB) {

        int clientId;

        do {
            clientId = myDB.getInt(Database.TABLE_CLIENTS, Database.CLIENT_ID, Database.CLIENT_PERSON_ID, personId);
            if (clientId != -1) super.deleteData(clientId, myDB);
        } while (clientId != -1);

        myDB.delete(Database.TABLE_PERSONS, Database.PERSON_ID, personId);

    }
}

package com.apps.szpansky.ajwon_app.SimpleData;

import android.database.Cursor;
import android.widget.ListView;

import com.apps.szpansky.ajwon_app.R;
import com.apps.szpansky.ajwon_app.Tools.Database;


public class Person extends Client {

    public static int clickedPersonId;


    @Override
    public int getItemLayoutResourceId(){

        return (R.layout.item_person_view);
    }


    @Override
    public Cursor setCursor(Database myDB){

        return myDB.getPersons(this.filter);
    }


    @Override
    public int[] getToViewIDs(){

        return (new int[]{
                R.id.personName,
                R.id.personSurname,
                R.id.personPhone,
                R.id.personAddress
        });
    }


    @Override
    public String[] getFromFieldsNames(){

        return new String[]{
                Database.PERSON_NAME,
                Database.PERSON_SURNAME,
                Database.PERSON_PHONE,
                Database.PERSON_ADDRESS
        };
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

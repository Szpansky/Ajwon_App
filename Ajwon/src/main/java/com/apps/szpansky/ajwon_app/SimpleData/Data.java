package com.apps.szpansky.ajwon_app.SimpleData;

import com.apps.szpansky.ajwon_app.Tools.Database;


public abstract class Data {
    protected Database myDB;

    public Data(Database myDB) {
        this.myDB = myDB;
    }


    public abstract void deleteData(int id);



}

package com.apps.szpansky.avon_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 *
 *
 * Created by Marcin on 2017-01-27.
 */

public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Avon.db";    //cala baza

    public static final String TABLE_WORKS = "WORKS";    //tabela
    public static final String WORK_CATALOG_NR = "CATALOG_NR";      //PRIMARY KEY
    public static final String WORK_PERSONS = "PERSONS";      //FOREIGN KEY WITH TABLE_PERSONS COLUMN PERSON_ID
    public static final String WORK_DATE = "DATE";


    public static final String TABLE_PERSONS = "PERSONS";    //tabela
    public static final String PERSON_ID = "ID";      //PRIMARY KEY
    public static final String PERSON_NAME = "NAME";
    public static final String PERSON_SURNAME = "SURNAME";
    public static final String PERSON_ADDRESS = "ADDRESS";
    public static final String PERSON_ITEMS = "ITEMS";               //FOREIGN KEY WITH ITEMS_NR
    public static final String PERSON_DATE = "DATE";


    public static final String TABLE_ITEMS = "ITEMS";    //tabela
    public static final String ITEM_NR = "ITEM_NR";   //PRIMARY KEY
    public static final String ITEM_PRICE = "PRICE";
    public static final String ITEM_DISCOUNT = "DISCOUNT";





    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_WORKS +" ("+
                WORK_CATALOG_NR +" INTEGER," +
                WORK_PERSONS +" INTEGER," +
                WORK_DATE +" TEXT," +
                "PRIMARY KEY ("+ WORK_CATALOG_NR +"), "+
                "FOREIGN KEY ("+ WORK_PERSONS +") REFERENCES "+ TABLE_PERSONS +" ("+ PERSON_ID +"))");

        db.execSQL("create table " + TABLE_PERSONS +" ("+
                PERSON_ID +" INTEGER," +
                PERSON_NAME +" TEXT," +
                PERSON_SURNAME +" TEXT," +
                PERSON_ADDRESS +" TEXT," +
                PERSON_ITEMS +" INTEGER," +
                PERSON_DATE +" TEXT," +
                "PRIMARY KEY ("+ PERSON_ID +")," +
                "FOREIGN KEY ("+ PERSON_ITEMS +") REFERENCES "+ TABLE_ITEMS +" ("+ ITEM_NR +"))");

        db.execSQL("create table " + TABLE_ITEMS +" ("+
                ITEM_NR +" INTEGER," +
                ITEM_PRICE +" DOUBLE," +
                ITEM_DISCOUNT +" INTEGER," +
                "PRIMARY KEY ("+ ITEM_NR +"))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
    }
}




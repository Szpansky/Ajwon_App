package com.apps.szpansky.avon_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 * Created by Marcin on 2017-01-27.
 */

public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Avon2.db";    //cala baza

    public static final String TABLE_WORKS = "WORKS";    //tabela
    public static final String WORKS_CATALOG_NR = "CATALOG_NR";      //PRIMARY KEY
    public static final String WORKS_DATE = "DATE";


    public static final String TABLE_PERSONS = "PERSONS";    //tabela
    public static final String PERSONS_CATALOG_NR = "CATALOG_NR";      //PRIMARY KEY
    public static final String PERSONS_NAME = "NAME";
    public static final String PERSONS_SURNAME = "SURNAME";
    public static final String PERSONS_ADDRESS = "ADDRESS";
    public static final String PERSONS_ID_ITEMS = "ITEMS";               //PRIMARY KEY
    public static final String PERSONS_DATE = "DATE";


    public static final String TABLE_ITEMS = "ITEMS";    //tabela
    public static final String ITEMS_ID_ITEMS = "ITEMS";               //PRIMARY KEY
    public static final String ITEMS_ITEM_NR = "ITEM_NR";
    public static final String ITEMS_PRICE = "PRICE";
    public static final String ITEMS_DISCOUNT = "DISCOUNT";





    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_WORKS +
                " (WORKS_CATALOG_NR INTEGER , " +
                "WORKS_DATE TEXT) " +
                "PRIMARY KEY (WORKS_CATALOG_NR))");

        db.execSQL("create table " + TABLE_PERSONS +
                " (PERSONS_CATALOG_NR INTEGER," +
                "PERSONS_NAME TEXT,PERSONS_SURNAME TEXT," +
                "PERSONS_ADDRESS TEXT,PERSONS_ID_ITEMS INTEGER," +
                "PERSONS_DATE TEXT," +
                "PRIMARY KEY (PERSONS_CATALOG_NR)) " +
                "FOREIGN KEY (PERSONS_ID_ITEMS) " +
                "REFERENCES "+ TABLE_ITEMS + "(ITEMS_ID_ITEMS)");

        db.execSQL("create table " + TABLE_ITEMS +
                " (ITEMS_ID_ITEMS INTEGER," +
                "ITEMS_ITEM_NR INTEGER," +
                "ITEMS_PRICE DOUBLE," +
                "ITEMS_DISCOUNT INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
    }
}



























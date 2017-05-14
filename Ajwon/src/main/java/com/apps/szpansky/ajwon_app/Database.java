package com.apps.szpansky.ajwon_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Marcin on 2017-01-27.
 */

public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Ajwon.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_WORKS = "WORKS";    //tabela
    public static final String WORK_CATALOG_NR = "_id";      //PK
    public static final String WORK_DATE = "DATE";              //AUTO DATE

    public static final String[] ALL_KEYS_WORK = new String[]{WORK_CATALOG_NR, WORK_DATE};

    public static final String TABLE_CLIENTS = "CLIENTS";
    public static final String CLIENT_ORDER_ID = "_id";      //PRIMARY KEY
    public static final String CLIENT_ID = "CLIENT_ID";          //FK TO PERSON_ID IN TABLE_PERSONS
    public static final String CLIENT_CARALOG_NR = "CATALOG_NR";   //FK TO CATALOG_NR IN WORKS
    public static final String CLIENT_DATE = "DATE";         //AUTO DATE


    public static final String TABLE_ORDERS = "ORDERS";    //tabela
    public static final String ORDER_ID = "ORDER_ID";        //PRIMARY KEY
    public static final String ORDER_ITEM_ID = "ITEM_ID";     //FK TO ITEM_ID IN TABLE_ITEMS
    public static final String ORDER_CLIENT_ID = "CLIENT_ID";     //FK TO PERSON_ID IN TABLE_PERSONS
    public static final String ORDER_STATUS = "STATUS";


    public static final String TABLE_PERSONS = "PERSONS";    //tabela
    public static final String PERSON_ID = "_id";      //PRIMARY KEY
    public static final String PERSON_NAME = "NAME";
    public static final String PERSON_SURNAME = "SURNAME";
    public static final String PERSON_ADDRESS = "ADDRESS";
    public static final String PERSON_PHONE = "PHONE";

    public static final String[] ALL_KEYS_PERSONS = new String[]{PERSON_ID, PERSON_NAME, PERSON_SURNAME, PERSON_PHONE, PERSON_ADDRESS};

    public static final String TABLE_ITEMS = "ITEMS";    //tabela
    public static final String ITEM_CATALOG_NR = "_id";   //PRIMARY KEY
    public static final String ITEM_PRICE = "PRICE";
    public static final String ITEM_NAME = "NAME";
    public static final String ITEM_DISCOUNT = "DISCOUNT";

    public static final String[] ALL_KEYS_ITEMS = new String[]{ITEM_CATALOG_NR,ITEM_NAME,ITEM_PRICE,ITEM_DISCOUNT};


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_WORKS +" ("+
                WORK_CATALOG_NR +" INTEGER PRIMARY KEY NOT NULL," +
                WORK_DATE +" DATETIME DEFAULT CURRENT_DATE)");




        db.execSQL("create table " + TABLE_CLIENTS +" ("+
                CLIENT_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+            //KLUCZ PODST AUTO INCREMENT
                CLIENT_ID +" INTEGER," +                //FK TO PERSONS_TABLE
                CLIENT_CARALOG_NR +" INTEGER," +        //FK TO WORK_CATALOG_NR IN TABLE_WORKS
                CLIENT_DATE +"  DATETIME DEFAULT CURRENT_DATE," +
                "FOREIGN KEY ("+ CLIENT_ID +") REFERENCES "+ TABLE_PERSONS +" ("+ PERSON_ID +")," +
                "FOREIGN KEY ("+ CLIENT_CARALOG_NR +") REFERENCES "+ TABLE_WORKS +" ("+ WORK_CATALOG_NR +"))");


        db.execSQL("create table " + TABLE_ORDERS +" ("+
                ORDER_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +             //PK
                ORDER_ITEM_ID +" INTEGER," +          // FK TO ITEM_ID IN TABLE_ITEMS
                ORDER_CLIENT_ID +" INTEGER," +      //FK TO CLIENT_ORDER_ID IN TABLE_ORDERS
                ORDER_STATUS +" BOOLEAN," +
                "FOREIGN KEY ("+ ORDER_ITEM_ID +") REFERENCES "+ TABLE_ITEMS +" ("+ ITEM_CATALOG_NR +"),"+
                "FOREIGN KEY ("+ ORDER_CLIENT_ID +") REFERENCES "+ TABLE_PERSONS +" ("+ PERSON_ID +"))");


        db.execSQL("create table " + TABLE_PERSONS +" ("+
                PERSON_ID +" INTEGER PRIMARY KEY NOT NULL," +        //PK
                PERSON_NAME +" TEXT," +
                PERSON_SURNAME +" TEXT," +
                PERSON_ADDRESS +" TEXT," +
                PERSON_PHONE +" LONG NOT NULL)");


        db.execSQL("create table " + TABLE_ITEMS +" ("+
                ITEM_CATALOG_NR +" INTEGER PRIMARY KEY NOT NULL," +          // PK
                ITEM_NAME +" TEXT NOT NULL," +
                ITEM_PRICE +" DOUBLE NOT NULL," +
                ITEM_DISCOUNT +" TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
    }

    public boolean insertDataToWorks(String catalog_nr){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WORK_CATALOG_NR, catalog_nr);
        long result = db.insert(TABLE_WORKS,null,contentValues);    //it return row, other way -1
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertDataToPersons(String name, String surname, String address, String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PERSON_NAME,name);
        contentValues.put(PERSON_SURNAME,surname);
        contentValues.put(PERSON_ADDRESS,address);
        contentValues.put(PERSON_PHONE,phone);
        long result = db.insert(TABLE_PERSONS,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    public boolean insertDataToItems(String nr, String name, String price, String discount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_CATALOG_NR,nr);
        contentValues.put(ITEM_NAME,name);
        contentValues.put(ITEM_PRICE,price);
        contentValues.put(ITEM_DISCOUNT,discount);
        long result = db.insert(TABLE_ITEMS,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }


    public Cursor getAllRows(String TABLE_NAME, String[] ROW_NAME, String ID){
        String[] where = null;            //TODO dodanie mozliwosci wyboru
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(true, TABLE_NAME, ROW_NAME, ID, where, null, null, null, null );
        if (c != null) {
            c.moveToFirst();
            }
        return c;
    }

    public boolean delete(String TABLE_NAME, long ROW_NAME, String ID){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = ID +" = "+ ROW_NAME;
        return db.delete(TABLE_NAME, where, null) != 0;
    }

}




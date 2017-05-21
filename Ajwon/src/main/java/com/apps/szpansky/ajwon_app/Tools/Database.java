package com.apps.szpansky.ajwon_app.Tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Ajwon.db";
    public static final int DATABASE_VERSION = 4;


    public static final String TABLE_WORKS = "WORKS";
    public static final String WORK_ID = "_id";
    public static final String WORK_DATE_START = "DATE_START";
    public static final String WORK_DATE_ENDS = "DATE_ENDS";

    public static final String[] ALL_KEYS_WORK = new String[]{WORK_ID, WORK_DATE_START, WORK_DATE_ENDS};


    public static final String TABLE_PERSONS = "PERSONS";
    public static final String PERSON_ID = "_id";
    public static final String PERSON_NAME = "NAME";
    public static final String PERSON_SURNAME = "SURNAME";
    public static final String PERSON_ADDRESS = "ADDRESS";
    public static final String PERSON_PHONE = "PHONE";

    public static final String[] ALL_KEYS_PERSONS = new String[]{PERSON_ID, PERSON_NAME, PERSON_SURNAME, PERSON_PHONE, PERSON_ADDRESS};


    public static final String TABLE_ITEMS = "ITEMS";
    public static final String ITEM_ID = "_id";
    public static final String ITEM_PRICE = "PRICE";
    public static final String ITEM_NAME = "NAME";
    public static final String ITEM_DISCOUNT = "DISCOUNT";

    public static final String[] ALL_KEYS_ITEMS = new String[]{ITEM_ID, ITEM_NAME, ITEM_PRICE, ITEM_DISCOUNT};


    public static final String TABLE_CLIENTS = "CLIENTS";
    public static final String CLIENT_ORDER_ID = "_id";
    public static final String CLIENT_WORK_ID = "WORK_ID";
    public static final String CLIENT_PERSON_ID = "PERSON_ID";
    public static final String CLIENT_DATE = "DATE";


    public static final String TABLE_ORDERS = "ORDERS";
    public static final String ORDER_ID = "_id";
    public static final String ORDER_CLIENT_ID = "PERSON_ID";
    public static final String ORDER_ITEM_ID = "ITEM_ID";
    public static final String ORDER_AMOUNT = "AMOUNT";
    public static final String ORDER_STATUS = "STATUS";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_WORKS + " (" +
                WORK_ID + " INTEGER PRIMARY KEY NOT NULL," +
                WORK_DATE_START + " DATETIME DEFAULT CURRENT_DATE," +
                WORK_DATE_ENDS + " DATETIME)");

        db.execSQL("create table " + TABLE_PERSONS + " (" +
                PERSON_ID + " INTEGER PRIMARY KEY NOT NULL," +
                PERSON_NAME + " TEXT," +
                PERSON_SURNAME + " TEXT," +
                PERSON_ADDRESS + " TEXT," +
                PERSON_PHONE + " LONG NOT NULL)");

        db.execSQL("create table " + TABLE_ITEMS + " (" +
                ITEM_ID + " INTEGER PRIMARY KEY NOT NULL," +
                ITEM_NAME + " TEXT NOT NULL," +
                ITEM_PRICE + " DOUBLE NOT NULL," +
                ITEM_DISCOUNT + " TEXT)");

        db.execSQL("create table " + TABLE_CLIENTS + " (" +
                CLIENT_ORDER_ID + " INTEGER PRIMARY KEY NOT NULL," +
                CLIENT_WORK_ID + " INTEGER," +
                CLIENT_PERSON_ID + " INTEGER," +
                CLIENT_DATE + "  DATETIME DEFAULT CURRENT_DATE," +
                "FOREIGN KEY (" + CLIENT_WORK_ID + ") REFERENCES " + TABLE_PERSONS + " (" + PERSON_ID + ")," +
                "FOREIGN KEY (" + CLIENT_PERSON_ID + ") REFERENCES " + TABLE_WORKS + " (" + WORK_ID + "))");

        db.execSQL("create table " + TABLE_ORDERS + " (" +
                ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ORDER_ITEM_ID + " INTEGER," +
                ORDER_CLIENT_ID + " INTEGER," +
                ORDER_AMOUNT + " INTEGER," +
                ORDER_STATUS + " BOOLEAN," +
                "FOREIGN KEY (" + ORDER_ITEM_ID + ") REFERENCES " + TABLE_ITEMS + " (" + ITEM_ID + ")," +
                "FOREIGN KEY (" + ORDER_CLIENT_ID + ") REFERENCES " + TABLE_PERSONS + " (" + PERSON_ID + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
    }


    public boolean insertDataToWorks(String workNr, String workDateEnd) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WORK_ID, workNr);
        contentValues.put(WORK_DATE_ENDS, workDateEnd);
        long result = db.insert(TABLE_WORKS, null, contentValues);    //it return row, other way -1
        if (result == -1)
            return false;
        else
            return true;
    }


    public boolean insertDataToPersons(String name, String surname, String address, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PERSON_NAME, name);
        contentValues.put(PERSON_SURNAME, surname);
        contentValues.put(PERSON_ADDRESS, address);
        contentValues.put(PERSON_PHONE, phone);
        long result = db.insert(TABLE_PERSONS, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }


    public boolean insertDataToItems(String nr, String name, String price, String discount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_ID, nr);
        contentValues.put(ITEM_NAME, name);
        contentValues.put(ITEM_PRICE, price);
        contentValues.put(ITEM_DISCOUNT, discount);
        long result = db.insert(TABLE_ITEMS, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }


    public Cursor getAllRows(String TABLE_NAME, String[] ROW_NAME, String ID) {
        String[] where = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(true, TABLE_NAME, ROW_NAME, ID, where, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }


    public Cursor getRow(String TABLE_NAME, String[] ALL_KEYS, long rowId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String where = "_id=" + rowId;
        Cursor c = db.query(true, TABLE_NAME, ALL_KEYS, where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }


    public boolean updateRowWork(long id, String workDateEnd) {
        SQLiteDatabase db = this.getReadableDatabase();
        String where = "_id=" + id;
        ContentValues newValues = new ContentValues();
        newValues.put(WORK_DATE_ENDS, workDateEnd);
        long result = db.update(TABLE_WORKS, newValues, where, null);
        if (result == -1)
            return false;
        else
            return true;
    }


    public boolean updateRowPerson(long id, String name, String surname, String address, String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        String where = "_id=" + id;
        ContentValues newValues = new ContentValues();
        newValues.put(PERSON_NAME, name);
        newValues.put(PERSON_SURNAME, surname);
        newValues.put(PERSON_ADDRESS, address);
        newValues.put(PERSON_PHONE, phone);
        long result = db.update(TABLE_PERSONS, newValues, where, null);
        if (result == -1)
            return false;
        else
            return true;
    }


    public boolean updateRowItem(long id, String name, String price, String discount) {
        SQLiteDatabase db = this.getReadableDatabase();
        String where = "_id=" + id;
        ContentValues newValues = new ContentValues();
        newValues.put(ITEM_NAME, name);
        newValues.put(ITEM_PRICE, price);
        newValues.put(ITEM_DISCOUNT, discount);
        long result = db.update(TABLE_ITEMS, newValues, where, null);
        if (result == -1)
            return false;
        else
            return true;
    }


    public boolean delete(String TABLE_NAME, long ROW_NAME, String ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = ID + " = " + ROW_NAME;
        return db.delete(TABLE_NAME, where, null) != 0;
    }

}




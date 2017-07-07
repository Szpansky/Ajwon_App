package com.apps.szpansky.ajwon_app.Tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Ajwon.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_CATALOGS = "CATALOGS";
    public static final String CATALOG_ID = "_id";
    public static final String CATALOG_DATE_START = "DATE_START";
    public static final String CATALOG_DATE_ENDS = "DATE_ENDS";

    public static final String TABLE_PERSONS = "PERSONS";
    public static final String PERSON_ID = "_id";
    public static final String PERSON_NAME = "NAME";
    public static final String PERSON_SURNAME = "SURNAME";
    public static final String PERSON_ADDRESS = "ADDRESS";
    public static final String PERSON_PHONE = "PHONE";
    public static final String PERSON_NETWORK_ID = "NETWORK_ID";

    public static final String TABLE_ITEMS = "ITEMS";
    public static final String ITEM_ID = "_id";
    public static final String ITEM_PRICE = "PRICE";
    public static final String ITEM_NAME = "NAME";
    public static final String ITEM_DISCOUNT = "DISCOUNT";

    public static final String TABLE_CLIENTS = "CLIENTS";
    public static final String CLIENT_ID = "_id";
    public static final String CLIENT_CATALOG_ID = "CATALOG_ID";
    public static final String CLIENT_PERSON_ID = "PERSON_ID";
    public static final String CLIENT_DATE = "DATE";
    public static final String CLIENT_STATUS = "STATUS";

    public static final String TABLE_ORDERS = "ORDERS";
    public static final String ORDER_ID = "_id";
    public static final String ORDER_CLIENT_ID = "PERSON_ID";
    public static final String ORDER_ITEM_ID = "ITEM_ID";
    public static final String ORDER_AMOUNT = "AMOUNT";
    public static final String ORDER_TOTAL = "TOTAL";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_CATALOGS + " (" +
                CATALOG_ID + " INTEGER PRIMARY KEY NOT NULL," +
                CATALOG_DATE_START + " DATETIME DEFAULT CURRENT_DATE," +
                CATALOG_DATE_ENDS + " DATETIME)");

        db.execSQL("create table " + TABLE_PERSONS + " (" +
                PERSON_ID + " INTEGER PRIMARY KEY NOT NULL," +
                PERSON_NAME + " TEXT," +
                PERSON_SURNAME + " TEXT," +
                PERSON_ADDRESS + " TEXT," +
                PERSON_PHONE + " LONG NOT NULL," +
                PERSON_NETWORK_ID + " INTEGER DEFAULT 0)");

        db.execSQL("create table " + TABLE_ITEMS + " (" +
                ITEM_ID + " INTEGER PRIMARY KEY NOT NULL," +
                ITEM_NAME + " TEXT," +
                ITEM_PRICE + " DOUBLE NOT NULL," +
                ITEM_DISCOUNT + " TEXT)");

        db.execSQL("create table " + TABLE_CLIENTS + " (" +
                CLIENT_ID + " INTEGER PRIMARY KEY NOT NULL," +
                CLIENT_CATALOG_ID + " INTEGER," +
                CLIENT_PERSON_ID + " INTEGER," +
                CLIENT_DATE + "  DATETIME DEFAULT CURRENT_DATE," +
                CLIENT_STATUS + " TEXT," +
                "FOREIGN KEY (" + CLIENT_CATALOG_ID + ") REFERENCES " + TABLE_CATALOGS + " (" + CATALOG_ID + ")," +
                "FOREIGN KEY (" + CLIENT_PERSON_ID + ") REFERENCES " + TABLE_PERSONS + " (" + PERSON_ID + "))");

        db.execSQL("create table " + TABLE_ORDERS + " (" +
                ORDER_ID + " INTEGER PRIMARY KEY NOT NULL," +
                ORDER_ITEM_ID + " INTEGER," +
                ORDER_CLIENT_ID + " INTEGER," +
                ORDER_AMOUNT + " INTEGER," +
                ORDER_TOTAL + " DOUBLE," +
                "FOREIGN KEY (" + ORDER_ITEM_ID + ") REFERENCES " + TABLE_ITEMS + " (" + ITEM_ID + ")," +
                "FOREIGN KEY (" + ORDER_CLIENT_ID + ") REFERENCES " + TABLE_CLIENTS + " (" + CLIENT_ID + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATALOGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
    }


    public boolean insertDataToCatalogs(String id, String dateStart, String dateEnd) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATALOG_ID, id);
        contentValues.put(CATALOG_DATE_START, dateStart);
        contentValues.put(CATALOG_DATE_ENDS, dateEnd);
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_CATALOGS, null, contentValues);    //it return row, other way -1
        if (result == -1)
            return false;
        else
            return true;
    }


    public boolean insertDataToPersons(String name, String surname, String address, String phone) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PERSON_NAME, name);
        contentValues.put(PERSON_SURNAME, surname);
        contentValues.put(PERSON_ADDRESS, address);
        contentValues.put(PERSON_PHONE, phone);

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_PERSONS, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }


    public boolean insertDataToItems(String nr, String name, String price, String discount) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_ID, nr);
        contentValues.put(ITEM_NAME, name);
        contentValues.put(ITEM_PRICE, price);
        contentValues.put(ITEM_DISCOUNT, discount);

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_ITEMS, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }


    public boolean insertDataToClients(String workId, String personId, String status) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT " + CLIENT_CATALOG_ID + ", " + CLIENT_PERSON_ID +
                        " FROM " + TABLE_CLIENTS +
                        " WHERE " + CLIENT_CATALOG_ID + " = " + workId + " AND " + CLIENT_PERSON_ID + "=" + personId
                , null);
        if (c.getCount() != 0) return false;     //if exist, quit from adding the same data

        ContentValues contentValues = new ContentValues();
        contentValues.put(CLIENT_CATALOG_ID, workId);
        contentValues.put(CLIENT_PERSON_ID, personId);
        contentValues.put(CLIENT_STATUS, status);

        db = this.getWritableDatabase();
        long result = db.insert(TABLE_CLIENTS, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }


    public boolean insertDataToOrders(String personId, String itemId, int amount) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT " + ITEM_PRICE + " " +
                        "FROM " + TABLE_ITEMS + " " +
                        "WHERE " + ITEM_ID + " = " + itemId
                , null);

        c.moveToFirst();
        double price = c.getDouble(0);
        double total = price * amount;

        ContentValues contentValues = new ContentValues();
        contentValues.put(ORDER_CLIENT_ID, personId);
        contentValues.put(ORDER_ITEM_ID, itemId);
        contentValues.put(ORDER_AMOUNT, amount);
        contentValues.put(ORDER_TOTAL, total);

        db = this.getWritableDatabase();
        long result = db.insert(TABLE_ORDERS, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }


    public Cursor getCatalogs(String filter) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * " +
                        "FROM " + TABLE_CATALOGS + " " +
                        "WHERE " +
                        CATALOG_ID + " LIKE \"%" + filter + "%\"" + " OR " +
                        CATALOG_DATE_START + " LIKE \"%" + filter + "%\"" + " OR " +
                        CATALOG_DATE_ENDS + " LIKE \"%" + filter + "%\"" + " " +
                        "ORDER BY " + CATALOG_DATE_START
                , null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }


    public Cursor getPersons(String filter) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * " +
                        "FROM " + TABLE_PERSONS + " " +
                        "WHERE " +
                        PERSON_NAME + " LIKE \"%" + filter + "%\"" + " OR " +
                        PERSON_SURNAME + " LIKE \"%" + filter + "%\"" + " OR " +
                        PERSON_ADDRESS + " LIKE \"%" + filter + "%\"" + " OR " +
                        PERSON_PHONE + " LIKE \"%" + filter + "%\"" + " " +
                        "ORDER BY " + PERSON_SURNAME
                , null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }


    public Cursor getItems(String filter) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * " +
                        "FROM " + TABLE_ITEMS + " " +
                        "WHERE " +
                        ITEM_ID + " LIKE \"%" + filter + "%\"" + " OR " +
                        ITEM_NAME + " LIKE \"%" + filter + "%\"" + " " +
                        "ORDER BY " + ITEM_NAME
                , null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }


    public Cursor getClients(Integer id, String filter) {
        SQLiteDatabase db = this.getReadableDatabase();
        String workId = id.toString();
        Cursor c = db.rawQuery(
                "SELECT * " +
                        "FROM " + TABLE_PERSONS + " AS P " +
                        "LEFT JOIN " + TABLE_CLIENTS + " AS C " +
                        "ON C." + CLIENT_PERSON_ID + " = P." + PERSON_ID + " " +
                        "WHERE " + CLIENT_CATALOG_ID + " = " + workId + " AND (" +
                        PERSON_NAME + " LIKE \"%" + filter + "%\"" + " OR " +
                        PERSON_SURNAME + " LIKE \"%" + filter + "%\"" + " OR " +
                        CLIENT_STATUS + " LIKE \"%" + filter + "%\"" + ")"
                , null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }


    public Cursor getOrders(Integer id, String filter) {
        SQLiteDatabase db = this.getReadableDatabase();
        String clientId = id.toString();
        Cursor c = db.rawQuery(
                "SELECT * " +
                        "FROM " + TABLE_ITEMS + " AS I " +
                        "LEFT JOIN " + TABLE_ORDERS + " AS O " +
                        "ON O." + ORDER_ITEM_ID + " = I." + ITEM_ID + " " +
                        "WHERE " + ORDER_CLIENT_ID + " = " + clientId + " " + " AND (" +
                        ITEM_NAME + " LIKE \"%" + filter + "%\"" + " OR " +
                        "I." + ITEM_ID + " LIKE \"%" + filter + "%\"" + ") " +
                        "ORDER BY " + ITEM_NAME
                , null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }


    public boolean updateRowCatalog(int id, String dateStart, String dateEnd) {
        SQLiteDatabase db = this.getReadableDatabase();
        String where = CATALOG_ID + " = " + id;
        ContentValues newValues = new ContentValues();
        newValues.put(CATALOG_DATE_START, dateStart);
        newValues.put(CATALOG_DATE_ENDS, dateEnd);
        long result = db.update(TABLE_CATALOGS, newValues, where, null);
        if (result == -1)
            return false;
        else
            return true;
    }


    public boolean updateRowPerson(int id, String name, String surname, String address, String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        String where = PERSON_ID + " = " + id;
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


    public boolean updateRowItem(int id, String name, String price, String discount) {
        SQLiteDatabase db = this.getReadableDatabase();
        String where = ITEM_ID + " = " + id;
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


    public boolean updateRowOrder(int clientId, int itemId, int count) {
        SQLiteDatabase db = this.getReadableDatabase();
        String where = ORDER_CLIENT_ID + " = " + clientId + " AND " + ORDER_ITEM_ID + " = " + itemId;

        Cursor c = db.rawQuery(
                "SELECT " + ITEM_PRICE +
                        " FROM " + TABLE_ITEMS +
                        " WHERE " + ITEM_ID + " = " + itemId, null);
        c.moveToFirst();
        double price = c.getDouble(0);

        c = db.rawQuery(
                "SELECT " + ORDER_AMOUNT +
                        " FROM " + TABLE_ORDERS +
                        " WHERE " + ORDER_CLIENT_ID + " = " + clientId + " AND " + ORDER_ITEM_ID + " = " + itemId, null);
        if (c.getCount() == 0) return false;
        c.moveToFirst();
        double amount = c.getDouble(0);

        amount = amount + count;
        double total = price * amount;

        ContentValues newValues = new ContentValues();
        newValues.put(ORDER_AMOUNT, amount);
        newValues.put(ORDER_TOTAL, total);
        long result = db.update(TABLE_ORDERS, newValues, where, null);
        if (result == -1)
            return false;
        else
            return true;
    }


    public Cursor getRow(String TABLE_NAME, String WHERE, int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT *" +
                        " FROM " + TABLE_NAME +
                        " WHERE " + WHERE + " = " + id, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }


    public int getInt(String TABLE_NAME, String ROW_NAME, String WHERE, int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT " + ROW_NAME +
                        " FROM " + TABLE_NAME +
                        " WHERE " + WHERE + " = " + id, null);

        if (c.getCount() == 0) return -1;
        c.moveToFirst();
        return c.getInt(0);
    }


    public double getDouble(String TABLE_NAME, String ROW_NAME, String WHERE, int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT " + ROW_NAME +
                        " FROM " + TABLE_NAME +
                        " WHERE " + WHERE + " = " + id, null);

        c.moveToFirst();

        return c.getDouble(0);
    }


    public boolean delete(String TABLE_NAME, String ROW_WHERE_ID, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = ROW_WHERE_ID + " = " + id;
        long result = db.delete(TABLE_NAME, where, null);
        if (result == -1)
            return false;
        else
            return true;
    }


}




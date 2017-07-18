package com.apps.szpansky.ajwon_app.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.apps.szpansky.ajwon_app.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.channels.FileChannel;


public final class FileManagement {


    public static void generateTXT(View view, Context context, String fileName, String tableName) {
        Database myDb = new Database(context);
        Cursor cursor = myDb.getTable(tableName);
        int columnCount = cursor.getColumnCount();
        int count = cursor.getCount();
        String toCopyLine = "";

        Snackbar snackbarInfo = Snackbar.make(view, R.string.wait_notify, Snackbar.LENGTH_INDEFINITE);
        snackbarInfo.show();

        try {
            File root = new File(Environment.getExternalStorageDirectory(), view.getResources().getString(R.string.app_name) + "/Exported");
            if (!root.exists()) {
                root.mkdirs();
            }

            File file = new File(root, fileName);

            FileOutputStream fileOut = new FileOutputStream(file, false);
            PrintWriter writer = new PrintWriter(fileOut);

            for (int x = 0; x < count; x++) {
                for (int y = 0; y < columnCount - 1; y++) {    //except the last one
                    toCopyLine = toCopyLine + cursor.getString(y) + "\t";
                }
                toCopyLine = toCopyLine + cursor.getString(columnCount - 1);     //the last one without "\t"

                writer.println(toCopyLine);
                cursor.moveToNext();
                toCopyLine = "";
            }

            writer.flush();
            writer.close();

            Snackbar snackbar = Snackbar.make(view, R.string.successfully_notify, Snackbar.LENGTH_SHORT);
            snackbar.show();

        } catch (IOException e) {
            Snackbar snackbar = Snackbar.make(view, R.string.error_notify, Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        myDb.close();
    }


    public static void importTXT(View view, Context context, String fileName, String tableName) {//TODO thread
        Integer updated = 0, created = 0;
        String where_query;


        Database myDB = new Database(context);
        Cursor cursor = myDB.getTable(tableName);
        int columnCount = cursor.getColumnCount();
        String[] columnsName = new String[columnCount];

        for (int y = 0; y < columnCount; y++) {
            columnsName[y] = cursor.getColumnName(y);
        }


        Snackbar snackbarInfo = Snackbar.make(view, R.string.wait_notify, Snackbar.LENGTH_INDEFINITE);
        snackbarInfo.show();
        try {
            File root = new File(Environment.getExternalStorageDirectory(), view.getResources().getString(R.string.app_name));
            File file = new File(root, fileName);
            if ((!root.exists()) || (!file.exists())) {
                Snackbar snackbarInfo1 = Snackbar.make(view, R.string.file_does_not_exists, Snackbar.LENGTH_SHORT);
                snackbarInfo1.show();
                return;
            }
            myDB.getWritableDatabase();
            ContentValues newValues = new ContentValues();

            FileInputStream fileIn = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(fileIn, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(reader);

            String copiedLine;
            String[] copiedCells;


            while ((copiedLine = bufferedReader.readLine()) != null) {
                copiedCells = copiedLine.split("\t");

                for (int y = 1; y < columnCount; y++) {         //from 1 bc without _id
                    newValues.put(columnsName[y], copiedCells[y]);
                }

                if (columnsName[columnCount-1].contains("UPDATE_DATE")) {

                    where_query = columnsName[0] + " = " + copiedCells[0] + " AND UPDATE_DATE" + " <  '" + copiedCells[columnCount-1]+"'";
                } else {
                    where_query = columnsName[0] + " = " + copiedCells[0];
                }

                boolean isUpdated = myDB.updateTable(tableName, newValues, where_query);
                if (isUpdated) {
                    updated++;
                    newValues.clear();
                } else {
                    newValues.clear();
                    for (int y = 0; y < columnCount; y++) {
                        newValues.put(columnsName[y], copiedCells[y]);
                    }
                    boolean isInserted = myDB.insertDataToTable(tableName, newValues);
                    if (isInserted) {
                        created++;
                        newValues.clear();
                    } else {
                        System.out.println("wartość aktualna");
                        newValues.clear();
                    }
                }
            }
            Snackbar snackbarInfo1 = Snackbar.make(view,
                    context.getResources().getString(R.string.updated) + updated +
                            context.getResources().getString(R.string.created) + created
                    , Snackbar.LENGTH_SHORT);
            snackbarInfo1.show();

        } catch (IOException e) {
            Snackbar snackbarInfo1 = Snackbar.make(view, R.string.error_notify, Snackbar.LENGTH_SHORT);
            snackbarInfo1.show();
        }
    }


    public static void importExportDB(View view, boolean export, String packageName) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {

                String currentDBPath = "//data//" + packageName
                        + "//databases//" + Database.DATABASE_NAME;
                String backupDBPath = "/" + view.getResources().getString(R.string.app_name) + "/" + Database.DATABASE_NAME;
                String appFolderPathOnSD = sd.getPath() + "/" + view.getResources().getString(R.string.app_name);

                File currentDB;
                File backupDB;

                if (export) {
                    File file = new File(appFolderPathOnSD);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    currentDB = new File(data, currentDBPath);
                    backupDB = new File(sd, backupDBPath);

                } else {
                    backupDB = new File(data, currentDBPath);
                    currentDB = new File(sd, backupDBPath);
                }

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

                Snackbar snackbar = Snackbar.make(view, R.string.successfully_notify, Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        } catch (Exception e) {
            Snackbar snackbar = Snackbar.make(view, R.string.backup_error, Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }
}

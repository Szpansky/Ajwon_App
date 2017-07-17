package com.apps.szpansky.ajwon_app.tools;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.apps.szpansky.ajwon_app.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;

public final class SimpleFunctions {

    public static String fillWithZeros(String value, int length) {
        while (value.length() < length) {
            value = "0" + value;
        }
        return value;
    }

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

                System.out.println(toCopyLine);
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


    public static void importExportDB(View view, boolean which, String packageName) {
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

                if (which) {
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

package com.apps.szpansky.ajwon_app.Tools;


import android.database.Cursor;
import android.widget.ListView;

/**
 * Created by Marcin on 2017-06-09.
 */

public interface DataInterface {

      void deleteData(int id, Database myDB);
      int getLayoutResourceId();
      int getItemLayoutResourceId();
      Cursor setCursor(Database myDB);
      String setTable();
      String[] setAllKeys();
      String setRowWhereId();
      int[] setToViewIDs();
      String[] setFromFieldsNames();




}

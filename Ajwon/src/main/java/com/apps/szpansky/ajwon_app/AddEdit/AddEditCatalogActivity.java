package com.apps.szpansky.ajwon_app.AddEdit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.szpansky.ajwon_app.SimpleData.Catalog;
import com.apps.szpansky.ajwon_app.Tools.Database;
import com.apps.szpansky.ajwon_app.R;


public class AddEditCatalogActivity extends AppCompatActivity {

    EditText workId;
    EditText workDateEnd;
    Button add;
    Database myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myDB = new Database(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_work);

        workId = (EditText) findViewById(R.id.workId);
        workDateEnd = (EditText) findViewById(R.id.workDateEnd);

        add = (Button) findViewById(R.id.add);


        Bundle b = getIntent().getExtras();
        Integer id = 0; // or other values
        Boolean edit = false; // or other values
        if (b != null) {

            id = Catalog.clickedCatalogId;


            edit = b.getBoolean("edit");
        }


        id = Catalog.clickedCatalogId;


        if (edit) {
            //String [] where = new String[]{String.valueOf("_id ="+id)};
            //Cursor cursor = myDB.getRow(Database.TABLE_ITEMS,Database.ALL_KEYS_ITEMS,id);

            //TODO get data from cursor -> to EditText
            workId.setText(id.toString());
            workId.setFocusable(false);


            //System.out.println(cursor.toString());
        }

        addData(id, edit);

    }

    public void addData(final int id, final boolean edit) {
        add.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (edit) {
                    boolean isUpdated = myDB.updateRowWork(id, workDateEnd.getText().toString());
                    if (isUpdated == true)
                        Toast.makeText(AddEditCatalogActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(AddEditCatalogActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();
                    finish();
                } else {

                    boolean isInserted = myDB.insertDataToWorks(workId.getText().toString(), workDateEnd.getText().toString());
                    if (isInserted == true)
                        Toast.makeText(AddEditCatalogActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(AddEditCatalogActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                    finish();

                }
            }
        }
        );

    }


}

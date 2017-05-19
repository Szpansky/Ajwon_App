package com.apps.szpansky.ajwon_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddWorkActivity extends AppCompatActivity {

    EditText catalogId;
    Button add;
    Database myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myDB = new Database(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work);

        catalogId = (EditText) findViewById(R.id.catalogId);
        add = (Button) findViewById(R.id.add);


        Bundle b = getIntent().getExtras();
        long id = 0; // or other values
        Boolean edit = false; // or other values
        if (b != null) {
            id = b.getLong("id");
            edit = b.getBoolean("edit");
        }

        if (edit) {
            //String [] where = new String[]{String.valueOf("_id ="+id)};
            //Cursor cursor = myDB.getRow(Database.TABLE_ITEMS,Database.ALL_KEYS_ITEMS,id);

            //TODO get data from cursor -> to EditText
            catalogId.setText(Long.toString(id));
            catalogId.setFocusable(false);


            //System.out.println(cursor.toString());
        }

        addData(id, edit);

    }

    public void addData(final long id, final boolean edit) {
        add.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (edit) {
                    boolean isUpdated = myDB.updateRowWork(id);
                    if (isUpdated == true)
                        Toast.makeText(AddWorkActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(AddWorkActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();
                    finish();
                } else {

                    boolean isInserted = myDB.insertDataToWorks(catalogId.getText().toString());
                    if (isInserted == true)
                        Toast.makeText(AddWorkActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(AddWorkActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                    finish();

                }
            }
        }
        );

    }


}

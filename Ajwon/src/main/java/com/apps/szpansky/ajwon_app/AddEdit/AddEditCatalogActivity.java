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

    private EditText workId;
    private EditText workDateEnd;
    private Button add;

    private Integer thisId = 0;
    private Boolean isEdit = false;

    private Database myDB;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_work);

        myDB = new Database(this);
        bundle = getIntent().getExtras();

        workId = (EditText) findViewById(R.id.workId);
        workDateEnd = (EditText) findViewById(R.id.workDateEnd);
        add = (Button) findViewById(R.id.add);

        if (bundle != null) {

            thisId = bundle.getInt("catalogId");
            isEdit = bundle.getBoolean("isEdit");
        }

        if (isEdit) {

            //TODO get data from cursor -> to EditText
            workId.setText(thisId.toString());
            workId.setFocusable(false);
        }

        addData(thisId, isEdit);
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
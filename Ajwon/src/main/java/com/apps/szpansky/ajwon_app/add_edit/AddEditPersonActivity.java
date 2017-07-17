package com.apps.szpansky.ajwon_app.add_edit;

import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.szpansky.ajwon_app.tools.Database;
import com.apps.szpansky.ajwon_app.R;

public class AddEditPersonActivity extends AppCompatActivity {

    private EditText name;
    private EditText surname;
    private EditText address;
    private EditText phone;
    private FloatingActionButton add;

    private Integer thisId = 0;
    private Boolean isEdit = false;

    private Database myDB;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_person);

        myDB = new Database(this);
        bundle = getIntent().getExtras();

        name = (EditText) findViewById(R.id.name);
        surname = (EditText) findViewById(R.id.surname);
        address = (EditText) findViewById(R.id.addres);
        phone = (EditText) findViewById(R.id.phone);
        add = (FloatingActionButton) findViewById(R.id.add);

        if (bundle != null) {

            thisId = bundle.getInt("personId");
            isEdit = bundle.getBoolean("isEdit");
        }

        if (isEdit) {

            name.setText(getPersonInfo(1));
            surname.setText(getPersonInfo(2));
            address.setText(getPersonInfo(3));
            phone.setText(getPersonInfo(4));
        }
        onAddClick();
    }


    private void onAddClick() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEdit) {
                    updateData();
                } else {
                    addData();
                }
            }
        });
    }


    private void updateData() {
        boolean isUpdated = myDB.updateRowPerson(thisId,
                name.getText().toString(),
                surname.getText().toString(),
                address.getText().toString(),
                phone.getText().toString());
        if (isUpdated) {
            Toast.makeText(AddEditPersonActivity.this, R.string.edit_client_notify, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AddEditPersonActivity.this, R.string.error_notify, Toast.LENGTH_LONG).show();
        }
        finish();
    }


    private void addData() {
        boolean isInserted = myDB.insertDataToPersons(
                name.getText().toString(),
                surname.getText().toString(),
                address.getText().toString(),
                phone.getText().toString());
        if (isInserted) {
            Toast.makeText(AddEditPersonActivity.this, R.string.add_client_notify, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AddEditPersonActivity.this, R.string.error_notify, Toast.LENGTH_LONG).show();
        }
        finish();
    }


    private String getPersonInfo(int columnIndex) {
        Cursor cursor = myDB.getRows(Database.TABLE_PERSONS, Database.PERSON_ID, thisId);
        cursor.moveToFirst();
        return cursor.getString(columnIndex);
    }
}

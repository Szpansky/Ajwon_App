package com.apps.szpansky.ajwon_app.AddEdit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.szpansky.ajwon_app.SimpleData.Person;
import com.apps.szpansky.ajwon_app.Tools.Database;
import com.apps.szpansky.ajwon_app.R;

public class AddEditPersonActivity extends AppCompatActivity {

    EditText name;
    EditText surname;
    EditText address;
    EditText phone;
    Button add;
    Database myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_person);

        myDB = new Database(this);

        name = (EditText)findViewById(R.id.name);
        surname = (EditText)findViewById(R.id.surname);
        address = (EditText)findViewById(R.id.addres);
        phone = (EditText)findViewById(R.id.phone);
        add = (Button)findViewById(R.id.add);


        Bundle b = getIntent().getExtras();
        int id = 0; // or other values
        Boolean edit = false; // or other values
        if (b != null) {

            id = Person.clickedPersonId;

            edit = b.getBoolean("edit");
        }

        id = Person.clickedPersonId;


        if (edit) {
            //String [] where = new String[]{String.valueOf("_id ="+id)};
            //Cursor cursor = myDB.getRow(Database.TABLE_ITEMS,Database.ALL_KEYS_ITEMS,id);

            //TODO get data from cursor -> to EditText
            //nr.setText(Long.toString(id));
            //nr.setFocusable(false);


            //System.out.println(cursor.toString());
        }

        addData(id,edit);
    }
    public void addData(final int id, final boolean edit) {
        add.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){

                        if (edit){
                            boolean isUpdated = myDB.updateRowPerson(id, name.getText().toString(),
                                    surname.getText().toString(),
                                    address.getText().toString(),
                                    phone.getText().toString());
                            if (isUpdated == true)
                                Toast.makeText(AddEditPersonActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(AddEditPersonActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else {

                            boolean isInserted = myDB.insertDataToPersons(name.getText().toString(),
                                    surname.getText().toString(),
                                    address.getText().toString(),
                                    phone.getText().toString());
                            if (isInserted == true)
                                Toast.makeText(AddEditPersonActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(AddEditPersonActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                            finish();

                        }
                    }
                }
        );

    }


}

package com.apps.szpansky.ajwon_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPersonActivity extends AppCompatActivity {

    EditText name;
    EditText surname;
    EditText address;
    EditText phone;
    Button add;
    Database myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        myDB = new Database(this);

        name = (EditText)findViewById(R.id.name);
        surname = (EditText)findViewById(R.id.surname);
        address = (EditText)findViewById(R.id.addres);
        phone = (EditText)findViewById(R.id.phone);
        add = (Button)findViewById(R.id.add);
        addData();
    }
    public void addData(){
        add.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        boolean isInserted = myDB.insertDataToPersons(name.getText().toString(),
                                                                        surname.getText().toString(),
                                                                        address.getText().toString(),
                                                                        phone.getText().toString());
                        if(isInserted == true)
                            Toast.makeText(AddPersonActivity.this,"Data Inserted",Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(AddPersonActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
        );

    }


}

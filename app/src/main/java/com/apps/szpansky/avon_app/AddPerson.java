package com.apps.szpansky.avon_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPerson extends AppCompatActivity {

    EditText text_name;
    EditText text_surname;
    EditText text_address;
    EditText text_phone;
    Button button_add;
    Database myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myDB = new Database(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);


        text_name = (EditText)findViewById(R.id.text_name);
        text_surname = (EditText)findViewById(R.id.text_surname);
        text_address = (EditText)findViewById(R.id.text_addres);
        text_phone = (EditText)findViewById(R.id.text_phone);
        button_add = (Button)findViewById(R.id.button_add);
        Add_Data();
    }
    public void Add_Data(){
        button_add.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        boolean isInserted = myDB.insert_data_persons(text_name.getText().toString(),
                                text_surname.getText().toString(),text_address.getText().toString(),
                                text_phone.getText().toString());
                        if(isInserted == true)
                            Toast.makeText(AddPerson.this,"Data Inserted",Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(AddPerson.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                    }
                }
        );

    }


}

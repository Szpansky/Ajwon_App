package com.apps.szpansky.avon_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddWork extends AppCompatActivity {

    EditText text_catalog;
    Button button_add;
    Database myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    myDB = new Database(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work);

        text_catalog = (EditText)findViewById(R.id.text_catalog);
        button_add = (Button)findViewById(R.id.button_add);
        Add_Data();
    }

    public void Add_Data(){

        button_add.setOnClickListener(
                new View.OnClickListener(){
            public void onClick(View v){
                boolean isInserted = myDB.insert_data_works(text_catalog.getText().toString());
            if(isInserted == true)
                Toast.makeText(AddWork.this,"Data Inserted",Toast.LENGTH_SHORT).show();
                else
                Toast.makeText(AddWork.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                finish();
            }
        }

        );
    }



}

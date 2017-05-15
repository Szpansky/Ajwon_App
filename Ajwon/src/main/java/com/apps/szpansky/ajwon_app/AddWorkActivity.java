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

        catalogId = (EditText)findViewById(R.id.catalogId);
        add = (Button)findViewById(R.id.add);
        addData();
    }

    public void addData(){

        add.setOnClickListener(
                new View.OnClickListener(){
            public void onClick(View v){
                boolean isInserted = myDB.insertDataToWorks(catalogId.getText().toString());
            if(isInserted == true)
                Toast.makeText(AddWorkActivity.this,"Data Inserted",Toast.LENGTH_SHORT).show();
                else
                Toast.makeText(AddWorkActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                finish();
            }
        }

        );
    }



}

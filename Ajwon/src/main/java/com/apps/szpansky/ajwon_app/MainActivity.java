package com.apps.szpansky.ajwon_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;




public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       Database myDB = new Database(this);          //create database

        Button openWorks = (Button) findViewById(R.id.openWorks);
        Button openPersons = (Button) findViewById(R.id.openPersons);
        Button openItems = (Button) findViewById(R.id.openItems);


        openWorks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent_Open_Works = new Intent(MainActivity.this, OpenWorksActivity.class);
                MainActivity.this.startActivity(Intent_Open_Works);

            }
        });

        openPersons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent_Open_Persons = new Intent(MainActivity.this, OpenPersonsActivity.class);
                MainActivity.this.startActivity(Intent_Open_Persons);
            }
        });

        openItems.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent Intent_Open_Items = new Intent(MainActivity.this, OpenItemsActivity.class);
                MainActivity.this.startActivity(Intent_Open_Items);
            }
        });

    }
}

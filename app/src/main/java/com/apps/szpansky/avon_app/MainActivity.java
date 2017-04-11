package com.apps.szpansky.avon_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;




public class MainActivity extends AppCompatActivity {

   // public Database myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


     //   myDB = new Database(this);          //create database


        Button button_new_work = (Button) findViewById(R.id.main_add);
        Button button_open_works = (Button) findViewById(R.id.main_open);
        Button button_add_person = (Button) findViewById(R.id.main_add_person);
        Button button_add_item = (Button) findViewById(R.id.main_add_item);

        button_new_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent_Add_Work = new Intent(MainActivity.this, AddWork.class);
                MainActivity.this.startActivity(Intent_Add_Work);
            }
        });

        button_open_works.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent_Open_Works = new Intent(MainActivity.this, OpenWorks.class);
                MainActivity.this.startActivity(Intent_Open_Works);

            }
        });

        button_add_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent_Add_Person = new Intent(MainActivity.this, AddPerson.class);
                MainActivity.this.startActivity(Intent_Add_Person);
            }
        });

        button_add_item.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent Intent_Add_Item = new Intent(MainActivity.this, AddItems.class);
                MainActivity.this.startActivity(Intent_Add_Item);
            }
        });

    }
}

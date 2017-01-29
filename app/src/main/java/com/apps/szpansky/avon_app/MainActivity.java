package com.apps.szpansky.avon_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Database myDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new Database(this);            //konstruktor tworzy baze


        Button button_new_work = (Button) findViewById(R.id.main_add);
        Button button_open_works = (Button) findViewById(R.id.main_open);

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



    }
}

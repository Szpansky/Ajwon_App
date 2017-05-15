package com.apps.szpansky.ajwon_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class AddItemsActivity extends AppCompatActivity {


    Database myDB;
    EditText name;
    EditText nr;
    EditText price;
    CheckBox dis_5;
    CheckBox dis_10;
    CheckBox dis_15;
    CheckBox dis_20;
    CheckBox dis_25;
    CheckBox dis_30;
    CheckBox dis_35;
    CheckBox dis_40;
    CheckBox dis_45;
    Button add;
    Integer discount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myDB = new Database(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

        name = (EditText) findViewById(R.id.name);
        nr = (EditText) findViewById(R.id.nr);
        price = (EditText) findViewById(R.id.price);
        dis_5 = (CheckBox) findViewById(R.id.check_5);
        dis_10 = (CheckBox) findViewById(R.id.check_10);
        dis_15 = (CheckBox) findViewById(R.id.check_15);
        dis_20 = (CheckBox) findViewById(R.id.check_20);
        dis_25 = (CheckBox) findViewById(R.id.check_25);
        dis_30 = (CheckBox) findViewById(R.id.check_30);
        dis_35 = (CheckBox) findViewById(R.id.check_35);
        dis_40 = (CheckBox) findViewById(R.id.check_40);
        dis_45 = (CheckBox) findViewById(R.id.check_45);
        add = (Button) findViewById(R.id.add);

        addData();
    }


    public void addData() {
        add.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if (dis_5.isChecked()) discount +=1;
                if (dis_10.isChecked()) discount +=10;
                if (dis_15.isChecked()) discount +=100;
                if (dis_20.isChecked()) discount +=1000;
                if (dis_25.isChecked()) discount +=10000;
                if (dis_30.isChecked()) discount +=100000;
                if (dis_35.isChecked()) discount +=1000000;
                if (dis_40.isChecked()) discount +=10000000;
                if (dis_45.isChecked()) discount +=100000000;

                boolean isInserted = myDB.insertDataToItems(nr.getText().toString(),
                        name.getText().toString(),
                        price.getText().toString(),
                        discount.toString());
                if (isInserted == true)
                    Toast.makeText(AddItemsActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(AddItemsActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                finish();
            }
        });


    }


}

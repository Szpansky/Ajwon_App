package com.apps.szpansky.avon_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class AddItems extends AppCompatActivity {


    Database myDB;
    EditText name;
    EditText nr;
    EditText price;
    CheckBox dis_10;
    CheckBox dis_20;
    CheckBox dis_25;
    Button add;
    String discount="9";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myDB = new Database(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);





        name = (EditText) findViewById(R.id.text_name);
        nr = (EditText) findViewById(R.id.text_nr);
        price = (EditText) findViewById(R.id.text_price);
        dis_10 = (CheckBox) findViewById(R.id.check_10);
        dis_20 = (CheckBox) findViewById(R.id.check_20);
        dis_25 = (CheckBox) findViewById(R.id.check_25);
        add = (Button) findViewById(R.id.button_add);

        Add_data();
    }

    public void Add_data(){
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dis_10.isChecked()==true) {
                    discount +="1";
                }else discount +="0";
                if (dis_20.isChecked()==true){
                    discount +="1";
                }else discount +="0";
                if (dis_25.isChecked()==true){
                    discount +="1";
                }else discount +="0";


                boolean isInserted = myDB.insert_data_item(nr.getText().toString(),
                                                            name.getText().toString(),
                                                            price.getText().toString(),
                                                            discount);
                if(isInserted == true)
                    Toast.makeText(AddItems.this,"Data Inserted",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(AddItems.this,"Data not Inserted",Toast.LENGTH_LONG).show();

            }
        });



    }



}

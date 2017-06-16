package com.apps.szpansky.ajwon_app.AddEdit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.szpansky.ajwon_app.SimpleData.Item;
import com.apps.szpansky.ajwon_app.Tools.Database;
import com.apps.szpansky.ajwon_app.R;


public class AddEditItemsActivity extends AppCompatActivity {

    private EditText name;
    private EditText nr;
    private EditText price;
    private CheckBox dis_5;
    private CheckBox dis_10;
    private CheckBox dis_15;
    private CheckBox dis_20;
    private CheckBox dis_25;
    private CheckBox dis_30;
    private CheckBox dis_35;
    private CheckBox dis_40;
    private CheckBox dis_100;
    private Button add;

    private Integer thisId = 0;
    private Integer discount = 0;
    private Boolean isEdit = false;

    private Database myDB;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_items);

        myDB = new Database(this);
        bundle = getIntent().getExtras();

        nr = (EditText) findViewById(R.id.nr);
        name = (EditText) findViewById(R.id.name);
        price = (EditText) findViewById(R.id.price);
        dis_5 = (CheckBox) findViewById(R.id.check_5);
        dis_10 = (CheckBox) findViewById(R.id.check_10);
        dis_15 = (CheckBox) findViewById(R.id.check_15);
        dis_20 = (CheckBox) findViewById(R.id.check_20);
        dis_25 = (CheckBox) findViewById(R.id.check_25);
        dis_30 = (CheckBox) findViewById(R.id.check_30);
        dis_35 = (CheckBox) findViewById(R.id.check_35);
        dis_40 = (CheckBox) findViewById(R.id.check_40);
        dis_100 = (CheckBox) findViewById(R.id.check_100);
        add = (Button) findViewById(R.id.add);

        if (bundle != null) {

            thisId = bundle.getInt("itemId");
            isEdit = bundle.getBoolean("isEdit");
        }

        if (isEdit) {

            //TODO get data from cursor -> to EditText
            nr.setText(thisId.toString());
            nr.setFocusable(false);
        }

        addData(thisId, isEdit);
    }


    public void addData(final int id, final boolean edit) {
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
                if (dis_100.isChecked()) discount +=100000000;

                if (edit){
                    boolean isUpdated = myDB.updateRowItem(id,
                            name.getText().toString(),
                            price.getText().toString(),
                            discount.toString());
                    if (isUpdated == true)
                        Toast.makeText(AddEditItemsActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(AddEditItemsActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
                    boolean isInserted = myDB.insertDataToItems(nr.getText().toString(),
                            name.getText().toString(),
                            price.getText().toString(),
                            discount.toString());
                    if (isInserted == true)
                        Toast.makeText(AddEditItemsActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(AddEditItemsActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
}

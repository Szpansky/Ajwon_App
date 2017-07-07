package com.apps.szpansky.ajwon_app.add_edit;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.szpansky.ajwon_app.tools.Database;
import com.apps.szpansky.ajwon_app.R;


public class AddEditItemsActivity extends AppCompatActivity {

    private EditText nr;
    private EditText price;
    private EditText name;

    private CheckBox dis_5;
    private CheckBox dis_10;
    private CheckBox dis_15;
    private CheckBox dis_20;
    private CheckBox dis_25;
    private CheckBox dis_30;
    private CheckBox dis_35;
    private CheckBox dis_40;
    private CheckBox dis_100;

    private CheckBox[] dis_all = {dis_5, dis_10, dis_15, dis_20, dis_25, dis_30, dis_35, dis_40, dis_100};

    private Button add;

    private Integer thisId = 0;
    private Integer discount = 0;
    private String discountSTR;                ////////////////////////
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

        dis_all[0] = (CheckBox) findViewById(R.id.check_5);
        dis_all[1] = (CheckBox) findViewById(R.id.check_10);
        dis_all[2] = (CheckBox) findViewById(R.id.check_15);
        dis_all[3] = (CheckBox) findViewById(R.id.check_20);
        dis_all[4] = (CheckBox) findViewById(R.id.check_25);
        dis_all[5] = (CheckBox) findViewById(R.id.check_30);
        dis_all[6] = (CheckBox) findViewById(R.id.check_35);
        dis_all[7] = (CheckBox) findViewById(R.id.check_40);
        dis_all[8] = (CheckBox) findViewById(R.id.check_100);

        add = (Button) findViewById(R.id.add);

        if (bundle != null) {

            thisId = bundle.getInt("itemId");
            isEdit = bundle.getBoolean("isEdit");
        }

        if (isEdit) {

            nr.setText(getItemInfo(0));
            name.setText(getItemInfo(1));
            price.setText(getItemInfo(2));
            discountSTR = getItemInfo(3);

            for (int i = 0; i < discountSTR.length(); i++) {
                int discountRevert = discountSTR.length() - 1 - i;
                if (discountSTR.charAt((discountRevert)) == '1') dis_all[i].setChecked(true);
            }
            nr.setFocusable(false);
        }
        addData(thisId, isEdit);
    }


    public void addData(final int id, final boolean edit) {
        add.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if (dis_all[0].isChecked()) discount += 1;
                if (dis_all[1].isChecked()) discount += 10;
                if (dis_all[2].isChecked()) discount += 100;
                if (dis_all[3].isChecked()) discount += 1000;
                if (dis_all[4].isChecked()) discount += 10000;
                if (dis_all[5].isChecked()) discount += 100000;
                if (dis_all[6].isChecked()) discount += 1000000;
                if (dis_all[7].isChecked()) discount += 10000000;
                if (dis_all[8].isChecked()) discount += 100000000;

                if (edit) {
                    boolean isUpdated = myDB.updateRowItem(id,
                            name.getText().toString(),
                            price.getText().toString(),
                            discount.toString());
                    if (isUpdated == true)
                        Toast.makeText(AddEditItemsActivity.this, R.string.edit_item_notify, Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(AddEditItemsActivity.this, R.string.error_notify, Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    boolean isInserted = myDB.insertDataToItems(nr.getText().toString(),
                            name.getText().toString(),
                            price.getText().toString(),
                            discount.toString());
                    if (isInserted == true)
                        Toast.makeText(AddEditItemsActivity.this, R.string.add_item_notify, Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(AddEditItemsActivity.this, R.string.error_notify, Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }


    private String getItemInfo(int columnIndex) {
        Cursor cursor = myDB.getRow(Database.TABLE_ITEMS, Database.ITEM_ID, thisId);
        cursor.moveToFirst();
        return cursor.getString(columnIndex);
    }
}

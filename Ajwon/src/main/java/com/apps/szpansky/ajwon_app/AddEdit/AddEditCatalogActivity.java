package com.apps.szpansky.ajwon_app.AddEdit;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import android.widget.Toast;

import com.apps.szpansky.ajwon_app.Tools.Database;
import com.apps.szpansky.ajwon_app.R;


public class AddEditCatalogActivity extends AppCompatActivity {

    private EditText catalogId;
    private EditText catalogDateEnd;
    private EditText catalogDateStart;
    private Button add;

    private Integer thisId = 0;
    private Boolean isEdit = false;

    private Database myDB;
    private Bundle bundle;

    private int year_x, month_x, day_x;

    static final int DIALOG_ID_START = 0;
    static final int DIALOG_ID_END = 1;


    public AddEditCatalogActivity() {
        Calendar calendar = Calendar.getInstance();
        this.year_x = calendar.get(Calendar.YEAR);
        this.month_x = calendar.get(Calendar.MONTH);
        this.day_x = calendar.get(Calendar.DAY_OF_MONTH);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_catalog);

        myDB = new Database(this);
        bundle = getIntent().getExtras();

        catalogId = (EditText) findViewById(R.id.catalogId);
        catalogDateStart = (EditText) findViewById(R.id.catalogDateStart);
        catalogDateEnd = (EditText) findViewById(R.id.catalogDateEnd);
        add = (Button) findViewById(R.id.add);

        if (bundle != null) {

            thisId = bundle.getInt("catalogId");
            isEdit = bundle.getBoolean("isEdit");
        }

        if (isEdit) {

            catalogId.setText(getCatalogInfo(0));
            catalogDateStart.setText(getCatalogInfo(1));
            catalogDateEnd.setText(getCatalogInfo(2));

            catalogId.setFocusable(false);
        }

        showDialogOnDateClick();
        addData(thisId, isEdit);
    }


    private void addData(final int id, final boolean edit) {
        add.setOnClickListener(new View.OnClickListener() {

                                   public void onClick(View v) {
                                       if (edit) {
                                           boolean isUpdated = myDB.updateRowCatalog(id, catalogDateStart.getText().toString(), catalogDateEnd.getText().toString());
                                           if (isUpdated == true)
                                               Toast.makeText(AddEditCatalogActivity.this, R.string.edit_catalog_notify, Toast.LENGTH_SHORT).show();
                                           else
                                               Toast.makeText(AddEditCatalogActivity.this, R.string.error_notify, Toast.LENGTH_LONG).show();
                                           finish();
                                       } else {
                                           boolean isInserted = myDB.insertDataToCatalogs(catalogId.getText().toString(), catalogDateStart.getText().toString(), catalogDateEnd.getText().toString());
                                           if (isInserted == true)
                                               Toast.makeText(AddEditCatalogActivity.this, R.string.add_catalog_notify, Toast.LENGTH_SHORT).show();
                                           else
                                               Toast.makeText(AddEditCatalogActivity.this, R.string.error_notify, Toast.LENGTH_LONG).show();
                                           finish();
                                       }
                                   }
                               }
        );
    }


    private String getCatalogInfo(int columnIndex) {
        Cursor cursor = myDB.getRow(Database.TABLE_CATALOGS, Database.CATALOG_ID, thisId);
        cursor.moveToFirst();
        return cursor.getString(columnIndex);
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                return new DatePickerDialog(this, dateStart, year_x, month_x, day_x);
            case 1:
                return new DatePickerDialog(this, dateEnd, year_x, month_x, day_x);
            default:
                return null;
        }
    }


    private DatePickerDialog.OnDateSetListener dateStart = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x = year;
            month_x = month + 1;
            day_x = dayOfMonth;
            catalogDateStart.setText(year_x + "-" + month_x + "-" + day_x);
        }
    };


    private DatePickerDialog.OnDateSetListener dateEnd = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x = year;
            month_x = month + 1;
            day_x = dayOfMonth;
            catalogDateEnd.setText(year_x + "-" + month_x + "-" + day_x);
        }
    };


    public void showDialogOnDateClick() {
        catalogDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID_START);
            }
        });

        catalogDateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID_END);
            }
        });
    }


}

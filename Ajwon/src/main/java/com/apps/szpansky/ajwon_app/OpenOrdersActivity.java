package com.apps.szpansky.ajwon_app;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class OpenOrdersActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_orders);


        Bundle b = getIntent().getExtras();
        long value = 0; // or other values
        if(b != null)
            value = b.getLong("id");

        Toast.makeText(OpenOrdersActivity.this,"Id = "+ value,Toast.LENGTH_SHORT).show();

    }
}

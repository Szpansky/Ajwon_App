package com.apps.szpansky.ajwon_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class HelpAndOpinionActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button devContact, marketRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_and_opinion);

        setToolbar();

        onDevContactClick();

        onMarketRateClick();

    }


    private void onDevContactClick(){
        devContact = (Button) findViewById(R.id.sendMailToDev);
        devContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mailIntent = new Intent(Intent.ACTION_SEND);

                mailIntent.setType("plain/text");
                mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"ajwonapp@gmail.com"});
                mailIntent.putExtra(Intent.EXTRA_SUBJECT, "Ajwon App");
                mailIntent.putExtra(Intent.EXTRA_TEXT, "Here you can enter your content");

                startActivity(Intent.createChooser(mailIntent, "Send mail..."));
            }
        });
    }


    private void onMarketRateClick(){
        marketRate = (Button) findViewById(R.id.rateOnMarket);
        marketRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}
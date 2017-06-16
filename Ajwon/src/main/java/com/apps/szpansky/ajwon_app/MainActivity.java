package com.apps.szpansky.ajwon_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.apps.szpansky.ajwon_app.MainBrowsing.CatalogsActivity;
import com.apps.szpansky.ajwon_app.OpenAll.OpenAllItemsActivity;
import com.apps.szpansky.ajwon_app.OpenAll.OpenAllPersonsActivity;
import com.apps.szpansky.ajwon_app.OpenAll.OpenAllCatalogsActivity;


public class MainActivity extends AppCompatActivity {


    Button openCatalogs;
    Button openWorks;
    Button openPersons;
    Button openItems;


    private DrawerLayout aDrawerLayout;
    private ActionBarDrawerToggle aToggle;

    public Toolbar aToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        aToolbar = (Toolbar) findViewById(R.id.nav_action);
        //this.setSupportActionBar(aToolbar);
        setTitle("Menu");


        aDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        aToggle = new ActionBarDrawerToggle(this, aDrawerLayout,R.string.open,R.string.close);

        aDrawerLayout.addDrawerListener(aToggle);
        aToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        openCatalogs = (Button) findViewById(R.id.openCatalogs);
        openWorks = (Button) findViewById(R.id.openWorks);
        openPersons = (Button) findViewById(R.id.openPersons);
        openItems = (Button) findViewById(R.id.openItems);

        onStartClick();
        onWorkClick();
        onPersonClick();
        onItemsClick();
    }


    private void onStartClick() {
        openCatalogs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent Intent_Open_Catalogs = new Intent(MainActivity.this, CatalogsActivity.class);
                MainActivity.this.startActivity(Intent_Open_Catalogs);
            }
        });
    }

    private void onWorkClick() {
        openWorks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent_Open_Works = new Intent(MainActivity.this, OpenAllCatalogsActivity.class);
                MainActivity.this.startActivity(Intent_Open_Works);
            }
        });
    }

    private void onPersonClick() {
        openPersons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent_Open_Persons = new Intent(MainActivity.this, OpenAllPersonsActivity.class);
                MainActivity.this.startActivity(Intent_Open_Persons);
            }
        });
    }

    private void onItemsClick() {
        openItems.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent Intent_Open_Items = new Intent(MainActivity.this, OpenAllItemsActivity.class);
                MainActivity.this.startActivity(Intent_Open_Items);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(aToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

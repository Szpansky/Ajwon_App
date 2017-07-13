package com.apps.szpansky.ajwon_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.apps.szpansky.ajwon_app.main_browsing.CatalogsActivity;
import com.apps.szpansky.ajwon_app.open_all.OpenAllItemsActivity;
import com.apps.szpansky.ajwon_app.open_all.OpenAllPersonsActivity;
import com.apps.szpansky.ajwon_app.open_all.OpenAllCatalogsActivity;
import com.apps.szpansky.ajwon_app.tools.Database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;


public class MainActivity extends AppCompatActivity {

    private final static boolean EXPORT = true;
    private final static boolean IMPORT = false;

    Button openCatalogs;

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        openCatalogs = (Button) findViewById(R.id.openCatalogs);
        navigationView = (NavigationView) findViewById(R.id.navView);

        setToolBar();
        setDrawerWithToggle();

        onStartClick();

        onNavigationItemClick();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    private void setDrawerWithToggle() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void onNavigationItemClick() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case (R.id.menuWorks):
                        drawerLayout.closeDrawers();
                        Intent Intent_Open_Works = new Intent(MainActivity.this, OpenAllCatalogsActivity.class);
                        MainActivity.this.startActivity(Intent_Open_Works);
                        break;
                    case (R.id.menuClients):
                        drawerLayout.closeDrawers();
                        Intent Intent_Open_Persons = new Intent(MainActivity.this, OpenAllPersonsActivity.class);
                        MainActivity.this.startActivity(Intent_Open_Persons);
                        break;
                    case (R.id.menuItems):
                        drawerLayout.closeDrawers();
                        Intent Intent_Open_Items = new Intent(MainActivity.this, OpenAllItemsActivity.class);
                        MainActivity.this.startActivity(Intent_Open_Items);
                        break;
                    case (R.id.menuExportDB):
                        importExportDB(EXPORT);
                        break;
                    case (R.id.menuImportDB):
                        importExportDB(IMPORT);
                        break;
                }
                return false;
            }
        });
    }


    private void onStartClick() {
        openCatalogs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent Intent_Open_Catalogs = new Intent(MainActivity.this, CatalogsActivity.class);
                MainActivity.this.startActivity(Intent_Open_Catalogs);
            }
        });
    }


    private void importExportDB(boolean which) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//" + getPackageName()
                        + "//databases//" + Database.DATABASE_NAME;
                String backupDBPath = "/" + getString(R.string.app_name) + "/" + Database.DATABASE_NAME;
                String appFolderPathOnSD = sd.getPath() + "/" + getString(R.string.app_name);

                File currentDB;
                File backupDB;

                if (which) {
                    File file = new File(appFolderPathOnSD);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    currentDB = new File(data, currentDBPath);
                    backupDB = new File(sd, backupDBPath);

                } else {
                    backupDB = new File(data, currentDBPath);
                    currentDB = new File(sd, backupDBPath);
                }

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

                Toast.makeText(this, R.string.successfully_notify, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Nie Znaleziono Bazy", Toast.LENGTH_LONG)
                    .show();
        }
    }
}

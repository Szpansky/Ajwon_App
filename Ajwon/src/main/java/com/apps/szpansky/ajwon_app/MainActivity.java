package com.apps.szpansky.ajwon_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

import com.apps.szpansky.ajwon_app.add_edit.AddEditCatalogActivity;
import com.apps.szpansky.ajwon_app.add_edit.AddEditItemsActivity;
import com.apps.szpansky.ajwon_app.add_edit.AddEditPersonActivity;
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
    private static boolean FLOATING_MENU = false;

    Button openCatalogs;
    FloatingActionButton fabMain, fabNewCatalog, fabNewPerson, fabNewItem;

    Animation fabClose, fabOpen, fabRotate, fabRotateBack;

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigationView;
    GridLayout subFloatingMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        navigationView = (NavigationView) findViewById(R.id.navView);
        openCatalogs = (Button) findViewById(R.id.openCatalogs);

        fabMain = (FloatingActionButton) findViewById(R.id.fabMain);
        fabNewCatalog = (FloatingActionButton) findViewById(R.id.fabAddCatalog);
        fabNewPerson = (FloatingActionButton) findViewById(R.id.fabAddPerson);
        fabNewItem = (FloatingActionButton) findViewById(R.id.fabAddItem);

        subFloatingMenu = (GridLayout) findViewById(R.id.subFloatingMenu);

        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);
        fabRotate = AnimationUtils.loadAnimation(this, R.anim.fab_rotate);
        fabRotateBack = AnimationUtils.loadAnimation(this, R.anim.fab_rotate_back);

        setToolBar();
        setDrawerWithToggle();

        onStartClick();
        onFloatingButtonClick();

        onNavigationItemClick();

        onFabMenuItemClick();
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

    private void dialogLoginBuilder(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_login, null);
        EditText email = (EditText) view.findViewById(R.id.dialog_text_email);
        EditText password = (EditText) view.findViewById(R.id.dialog_text_password);
        Button login = (Button) view.findViewById(R.id.dialog_button_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Logowanie wkrótce", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setView(view);
        builder.create();
        builder.show();
    }


    private void dialogInformationBuilder(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_information, null);

        builder.setView(view);
        builder.create();
        builder.show();
    }

    private synchronized void onNavigationItemClick() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case (R.id.menuLogin):
                        drawerLayout.closeDrawer(Gravity.START, false);
                        dialogLoginBuilder();
                        break;
                    case (R.id.menuMyAccount):
                        dialogInformationBuilder();
                        break;
                    case (R.id.menuWorks):
                        //drawerLayout.closeDrawer(Gravity.START, false);
                        Intent Intent_Open_Works = new Intent(MainActivity.this, OpenAllCatalogsActivity.class);
                        MainActivity.this.startActivity(Intent_Open_Works);
                        break;
                    case (R.id.menuClients):
                        //drawerLayout.closeDrawer(Gravity.START, false);
                        Intent Intent_Open_Persons = new Intent(MainActivity.this, OpenAllPersonsActivity.class);
                        MainActivity.this.startActivity(Intent_Open_Persons);
                        break;
                    case (R.id.menuItems):
                        //drawerLayout.closeDrawer(Gravity.START, false);
                        Intent Intent_Open_Items = new Intent(MainActivity.this, OpenAllItemsActivity.class);
                        MainActivity.this.startActivity(Intent_Open_Items);
                        break;
                    case (R.id.menuExportDB):
                        drawerLayout.closeDrawers();
                        importExportDB(EXPORT);
                        break;
                    case (R.id.menuImportDB):
                        drawerLayout.closeDrawers();
                        importExportDB(IMPORT);
                        break;
                }
                return true;
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


    private void onFloatingButtonClick() {
        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FLOATING_MENU) {
                    subFloatingMenu.startAnimation(fabClose);
                    fabMain.startAnimation(fabRotateBack);

                    fabNewCatalog.setClickable(false);
                    fabNewPerson.setClickable(false);
                    fabNewItem.setClickable(false);

                    subFloatingMenu.setVisibility(View.GONE);
                    FLOATING_MENU = false;
                } else {
                    subFloatingMenu.setVisibility(View.VISIBLE);
                    subFloatingMenu.startAnimation(fabOpen);
                    fabMain.startAnimation(fabRotate);

                    fabNewCatalog.setClickable(true);
                    fabNewPerson.setClickable(true);
                    fabNewItem.setClickable(true);

                    FLOATING_MENU = true;
                }
            }
        });
    }


    public void onFabMenuItemClick(){
        fabNewCatalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditCatalogActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        fabNewPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditPersonActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        fabNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditItemsActivity.class);
                MainActivity.this.startActivity(intent);
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
            Toast.makeText(this, R.string.backup_error, Toast.LENGTH_LONG)
                    .show();
        }
    }
}

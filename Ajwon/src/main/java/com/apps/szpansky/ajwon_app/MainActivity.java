package com.apps.szpansky.ajwon_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.apps.szpansky.ajwon_app.add_edit.AddEditCatalogActivity;
import com.apps.szpansky.ajwon_app.add_edit.AddEditItemsActivity;
import com.apps.szpansky.ajwon_app.add_edit.AddEditPersonActivity;
import com.apps.szpansky.ajwon_app.main_browsing.CatalogsActivity;
import com.apps.szpansky.ajwon_app.open_all.OpenAllItemsActivity;
import com.apps.szpansky.ajwon_app.open_all.OpenAllPersonsActivity;
import com.apps.szpansky.ajwon_app.open_all.OpenAllCatalogsActivity;
import com.apps.szpansky.ajwon_app.tools.Database;
import com.apps.szpansky.ajwon_app.tools.FileManagement;
import com.apps.szpansky.ajwon_app.tools.NetworkFunctions;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivity extends AppCompatActivity {

    public static boolean LOGGED = false;

    private final static boolean EXPORT = true;
    private final static boolean IMPORT = false;
    private static boolean FLOATING_MENU = false;

    private String tableName = Database.TABLE_ITEMS;    //default exported/imported
    private String fileName = "Items.txt";

    private Button openCatalogs;
    private FloatingActionButton fabMain, fabNewCatalog, fabNewPerson, fabNewItem;

    private Animation fabClose, fabOpen, fabRotate, fabRotateBack;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private GridLayout subFloatingMenu;

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;


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

        setAds();

        onStartADSClick();

        getPreferences();

    }


    private void getPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String test = sharedPreferences.getString("pref_edit_text_loggedAs", getResources().getString(R.string.logged_as));
        View v = navigationView.getHeaderView(0);
        TextView loggedAs = (TextView) v.findViewById(R.id.loggedAs);
        loggedAs.setText(test);
    }


    private void setAds() {
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.ads_Interstitial_main_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }


    private void onStartADSClick() {
        Button startAds = (Button) findViewById(R.id.startAd);
        startAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Snackbar snackbarInfo = Snackbar.make(findViewById(R.id.drawerLayout), R.string.try_again_later, Snackbar.LENGTH_SHORT);
                    snackbarInfo.show();
                }
            }
        });
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                Snackbar snackbarInfo = Snackbar.make(findViewById(R.id.drawerLayout), R.string.thank_you, Snackbar.LENGTH_SHORT);
                snackbarInfo.show();
            }
        });
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

        if (drawerLayout.isDrawerVisible(Gravity.START)) {
            navigationView.clearFocus();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void dialogLoginBuilder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_login, null);
        final EditText emailEditText = (EditText) dialogView.findViewById(R.id.dialog_text_email);
        final EditText passwordEditText = (EditText) dialogView.findViewById(R.id.dialog_text_password);
        Button loginButton = (Button) dialogView.findViewById(R.id.dialog_button_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                LOGGED = NetworkFunctions.logIn(email, password);

                if (LOGGED) {
                    Snackbar snackbar = Snackbar.make(dialogView, R.string.coming_soon, Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else {
                    Snackbar snackbar = Snackbar.make(dialogView, R.string.coming_soon, Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }

            }
        });
        builder.setView(dialogView);
        builder.create();
        builder.show();
    }


    private void dialogInformationBuilder() {
        final AlertDialog builder = new AlertDialog.Builder(this).create();
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_information, null);

        Button backButton = (Button) dialogView.findViewById(R.id.dialog_button_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });

        builder.setView(dialogView);
        builder.show();
    }


    private void dialogExportImportBuilder() {
        final AlertDialog builder = new AlertDialog.Builder(this).create();
        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_export_import, null);

        Button importDBButton = (Button) dialogView.findViewById(R.id.dialog_ie_button_db_import);
        Button exportDBButton = (Button) dialogView.findViewById(R.id.dialog_ie_button_db_export);
        final RadioGroup radioGroup = (RadioGroup) dialogView.findViewById(R.id.dialog_ie_radio_group);
        Button importTableButton = (Button) dialogView.findViewById(R.id.dialog_ie_button_folder_import);
        Button exportTableButton = (Button) dialogView.findViewById(R.id.dialog_ie_button_folder_export);

        importDBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileManagement.importExportDB(findViewById(R.id.drawerLayout), IMPORT, getPackageName());
                builder.dismiss();
            }
        });

        exportDBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileManagement.importExportDB(findViewById(R.id.drawerLayout), EXPORT, getPackageName());
                builder.dismiss();
            }
        });

        importTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileManagement.importTXT(dialogView.findViewById(R.id.dialog_import_export), getBaseContext(), fileName, tableName);
            }
        });

        exportTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileManagement.generateTXT(dialogView.findViewById(R.id.dialog_import_export), getBaseContext(), fileName, tableName);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case (R.id.dialog_ie_radio_items):
                        fileName = "Items.txt";
                        tableName = Database.TABLE_ITEMS;
                        break;
                    case (R.id.dialog_ie_radio_persons):
                        fileName = "Persons.txt";
                        tableName = Database.TABLE_PERSONS;
                        break;
                    case (R.id.dialog_ie_radio_catalogs):
                        fileName = "Catalogs.txt";
                        tableName = Database.TABLE_CATALOGS;
                        break;
                }
            }
        });
        builder.setView(dialogView);
        builder.show();
    }


    private void onNavigationItemClick() {
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
                        startActivity(Intent_Open_Works);
                        break;
                    case (R.id.menuClients):
                        //drawerLayout.closeDrawer(Gravity.START, false);
                        Intent Intent_Open_Persons = new Intent(MainActivity.this, OpenAllPersonsActivity.class);
                        startActivity(Intent_Open_Persons);
                        break;
                    case (R.id.menuItems):
                        //drawerLayout.closeDrawer(Gravity.START, false);
                        Intent Intent_Open_Items = new Intent(MainActivity.this, OpenAllItemsActivity.class);
                        startActivity(Intent_Open_Items);
                        break;
                    case (R.id.showTools):
                        Menu menu = navigationView.getMenu();
                        if (menu.findItem(R.id.subItems).isVisible()) {
                            menu.findItem(R.id.subItems).setVisible(false);
                            item.setIcon(R.mipmap.ic_keyboard_arrow_down_black_24dp);
                        } else {
                            menu.findItem(R.id.subItems).setVisible(true);
                            item.setIcon(R.mipmap.ic_keyboard_arrow_up_black_24dp);
                        }
                        break;
                    case (R.id.menuExportImport):
                        dialogExportImportBuilder();
                        break;
                    case (R.id.menuSetting):
                        Intent Intent_Open_Settings = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(Intent_Open_Settings);
                        break;
                    case (R.id.menuHelpOpinion):

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


    public void onFabMenuItemClick() {
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
}

package com.example.nathalieseibert.wells;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenueActivity extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String DEBUG_TAG = "Tag";
    Button wasserButton, hackerlButton;
    TextView mlview;
    EditText mltext;
    EditText email;
    TextView prozent;
    TextView titele;
    Switch mySwitch;
    DatabaseHelper databaseHelper;
    ProgressBar progressBar;
    public int wasserProgress;
    public int wasserbedarf = 2000;
    public int balken;
    public float rechnen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menue);

        databaseHelper = new DatabaseHelper(MainMenueActivity.this);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Das nächste sollte statt den addWater button auf den Benachrichtigung speichern Button bei den Einstellungen geändert werden!
        //Folgend wird täglich um 8:30 eine Notification erscheinen

        mySwitch = findViewById(R.id.mySwitch);
        progressBar = findViewById(R.id.progressBar);
        prozent = findViewById(R.id.textProzent);
        titele = findViewById(R.id.titele);
        progressanzeige();


//visibility buttons
        wasserButton = findViewById(R.id.addWater); //define Buttons for visibility
        hackerlButton = findViewById(R.id.haeckchen); //define ButtoQns for visibility
        mltext = findViewById(R.id.editWater);//define edittext for visibility
        mlview = findViewById(R.id.textMl);//define textview for visibility
        hackerlButton.setVisibility(View.GONE);
        mltext.setVisibility(View.GONE);
        mlview.setVisibility(View.GONE);

/*        mySwitch.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {


                                            //Is the switch on?
                                            boolean on = mySwitch.isChecked();

                                            if (on) {
                                                float myfloat = (float) wasserbedarf * 1.1f;
                                                wasserbedarf = Math.round(myfloat);
                                                progressanzeige();

                                            } else {

                                            }


                                        }
                                    }
        );

*/
        wasserButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                hackerlButton.setVisibility(View.VISIBLE);
                                                mltext.setVisibility(View.VISIBLE);
                                                mlview.setVisibility(View.VISIBLE);

                                            }
                                        }
        );
        hackerlButton.setOnClickListener(new View.OnClickListener() {
                                             @SuppressLint("SetTextI18n")
                                             @Override
                                             public void onClick(View v) {
                                                 hackerlButton.setVisibility(View.GONE);
                                                 mltext.setVisibility(View.GONE);
                                                 mlview.setVisibility(View.GONE);


                                                 String mail = getIntent().getStringExtra("Email");
//ToDo soll und datum berechnen und statische variablen tauschen

                                                 Boolean insertdataml = databaseHelper.insertml(mail, mltext.toString(), "500", "01.01.2010");
                                                 if (insertdataml) {
                                                     String eingabe = mltext.getText().toString();
                                                     Toast.makeText(getApplicationContext(), eingabe + "ml erfolgreich eingetragen", Toast.LENGTH_SHORT).show();
                                                 } else {
                                                     Toast.makeText(getApplicationContext(), "ml konnten nicht eingetragen werden", Toast.LENGTH_SHORT).show();
                                                 }

                                                 try {
                                                     String eingabe = mltext.getText().toString();
                                                     int zunahme = Integer.parseInt(eingabe);
                                                     wasserProgress = wasserProgress + zunahme;
                                                     progressanzeige();

                                                 } catch (Exception e) {
                                                     Toast.makeText(getApplicationContext(), "Fortschritt kann nicht angezeigt werden!", Toast.LENGTH_SHORT).show();
                                                 }


                                             }
                                         }
        );

    }

    public void progressanzeige() {

        rechnen = ((float) wasserProgress / (float) wasserbedarf) * 100;
        balken = Math.round(rechnen);
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", balken);
        animation.setDuration(1250);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
        prozent.setText(balken + "%");
        titele.setText(wasserProgress + "ml von " + wasserbedarf + "ml");

    }

    public void bedarfBerechnen(){

        // if (getAlter >= 15 <= 18 || getAlter >= 60){
        // float myfloat = (float) wasserbedarf * 0.85;
        // wasserbedarf = Math.round(myfloat);
        // }else if (getAlter < 15){
        // float myfloat = (float) wasserbedarf * 0.6;
        // wasserbedarf = Math.round(myfloat);
        // }else {}

        // if (getGewicht <= 60){
        // float myfloat = (float) wasserbedarf * 0.86;
        // wasserbedarf = Math.round(myfloat);
        // }else if (getGewicht > 100 && getGewicht <= 140){
        // float myfloat = (float) wasserbedarf * 1.14;
        // wasserbedarf = Math.round(myfloat);
        // }else if (getGewicht > 140){
        // float myfloat = (float) wasserbedarf * 1.24;
        // wasserbedarf = Math.round(myfloat);
        // else{}

        // if (getGroesse < 170 && getGroesse >= 155) {
        // float myfloat = (float) wasserbedarf * 0.9;
        // wasserbedarf = Math.round(myfloat);
        // } else if (getGroesse < 155){
        // float myfloat = (float) wasserbedarf * 0.83;
        // wasserbedarf = Math.round(myfloat);
        // }else if (getGroesse > 190){
        // float myfloat = (float) wasserbedarf * 1.11;
        // wasserbedarf = Math.round(myfloat);
        // else{}


        progressanzeige();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menue, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.homeButton) {
            startActivity(new Intent(this, MainMenueActivity.class));
            return true;

        }
//logOut button
        if (id == R.id.logOut) {

            startActivity(new Intent(this, LoginActivity.class));
            return true;

            // databaseHelper.close();

        }


        return super.onOptionsItemSelected(item);


    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_f1: {
                Trinkbrunnen trinkbrunnenfragement = new Trinkbrunnen();
                RelativeLayout mainLayout = findViewById(R.id.mainLayout);
                mainLayout.removeAllViews();
                android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.mainLayout, trinkbrunnenfragement, trinkbrunnenfragement.getTag()).commit();

                break;
            }
            case R.id.nav_f2: {
                Verlauf verlauffragment = new Verlauf();
                RelativeLayout mainLayout = findViewById(R.id.mainLayout);
                mainLayout.removeAllViews();
                android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.mainLayout, verlauffragment, verlauffragment.getTag()).commit();


                break;
            }
            case R.id.nav_f3: {
                Statisitk statisitkfragment = new Statisitk();
                RelativeLayout mainLayout = findViewById(R.id.mainLayout);
                mainLayout.removeAllViews();
                android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.mainLayout, statisitkfragment, statisitkfragment.getTag()).commit();

                break;
            }
            case R.id.nav_f4: {
                Einstellungen einstellungfragment = new Einstellungen();
                RelativeLayout mainLayout = findViewById(R.id.mainLayout);
                mainLayout.removeAllViews();
                android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.mainLayout, einstellungfragment, einstellungfragment.getTag()).commit();
                break;
            }
            case R.id.nav_f5: {
                startActivity(new Intent(this, LoginActivity.class));
                break;
            }

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}

package com.example.nathalieseibert.wells;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenueActivity extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener, SensorEventListener {

    private int wasserProgress;
    private int wasserbedarf = 2000;
    private Button hackerlButton;
    private TextView mlview;
    private EditText mltext;
    private TextView prozent;
    private TextView titele;
    private Switch mySwitch;
    private DatabaseHelper databaseHelper;
    private ProgressBar progressBar;
    private TextView temperaturelabel;
    private SensorManager mSensorManager;
    private Sensor mTemperature;
    private int temp;
    private int altwasser = wasserbedarf;
    private boolean schonberechnet = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
        Button wasserButton = findViewById(R.id.addWater);
        hackerlButton = findViewById(R.id.haeckchen); //define ButtoQns for visibility
        mltext = findViewById(R.id.editWater);//define edittext for visibility
        mlview = findViewById(R.id.textMl);//define textview for visibility
        hackerlButton.setVisibility(View.GONE);
        mltext.setVisibility(View.GONE);
        mlview.setVisibility(View.GONE);

        //temperatur
        temperaturelabel = findViewById(R.id.myTemp);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        bedarfBerechnen();


        TextView.OnEditorActionListener tveal = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        (event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                    hackerlButton.performClick();

                    return true;
                }
                return false;
            }
        };

        mltext.setOnEditorActionListener(tveal);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.waterfill);

        mySwitch.setOnClickListener(new View.OnClickListener() {


                                        @Override
                                        public void onClick(View v) {


                                            //Is the switch on?
                                            boolean on = mySwitch.isChecked();

                                            if (on) {
                                                float myfloat = (float) wasserbedarf * 1.1f;
                                                wasserbedarf = Math.round(myfloat);
                                                progressanzeige();

                                            } else {

                                                wasserbedarf = altwasser;
                                                progressanzeige();
                                            }


                                        }
                                    }
        );


        wasserButton.setOnClickListener(new View.OnClickListener()

                                        {
                                            @Override
                                            public void onClick(View v) {
                                                hackerlButton.setVisibility(View.VISIBLE);
                                                mltext.setVisibility(View.VISIBLE);
                                                mlview.setVisibility(View.VISIBLE);

                                            }
                                        }
        );
        hackerlButton.setOnClickListener(new View.OnClickListener()

                                         {
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

                                                     if (!schonberechnet) {
                                                         bedarfBerechnen();
                                                         if(mySwitch.isChecked()){
                                                             float myfloat = (float) wasserbedarf * 1.1f;
                                                             wasserbedarf = Math.round(myfloat);
                                                         }
                                                     }
                                                     schonberechnet = true;
                                                     progressanzeige();
                                                     mp.start();

                                                 } catch (Exception e) {
                                                     Toast.makeText(getApplicationContext(), "Fortschritt kann nicht angezeigt werden!", Toast.LENGTH_SHORT).show();
                                                 }


                                             }
                                         }
        );

    }



    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mTemperature, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float ambient_temperature = event.values[0];
        temp = Math.round(ambient_temperature);
        temperaturelabel.setText(temp + "°C");


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    private void progressanzeige() {

        float rechnen = ((float) wasserProgress / (float) wasserbedarf) * 100;
        int balken = Math.round(rechnen);
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", balken);
        animation.setDuration(1250);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
        prozent.setText(balken + "%");
        titele.setText(wasserProgress + "ml von " + wasserbedarf + "ml");


    }

    private void bedarfBerechnen() {

        // if (getAlter >= 15 <= 18 || getAlter >= 60){
        // float myfloat = (float) wasserbedarf * 0.85f;
        // wasserbedarf = Math.round(myfloat);
        // }else if (getAlter < 15){
        // float myfloat = (float) wasserbedarf * 0.6f;
        // wasserbedarf = Math.round(myfloat);
        // }else {}

        // if (getGewicht <= 60){
        // float myfloat = (float) wasserbedarf * 0.86f;
        // wasserbedarf = Math.round(myfloat);
        // }else if (getGewicht > 100 && getGewicht <= 140){
        // float myfloat = (float) wasserbedarf * 1.14f;
        // wasserbedarf = Math.round(myfloat);
        // }else if (getGewicht > 140){
        // float myfloat = (float) wasserbedarf * 1.24f;
        // wasserbedarf = Math.round(myfloat);
        // else{}

        // if (getGroesse < 170 && getGroesse >= 155) {
        // float myfloat = (float) wasserbedarf * 0.9f;
        // wasserbedarf = Math.round(myfloat);
        // } else if (getGroesse < 155){
        // float myfloat = (float) wasserbedarf * 0.83f;
        // wasserbedarf = Math.round(myfloat);
        // }else if (getGroesse > 190){
        // float myfloat = (float) wasserbedarf * 1.11f;
        // wasserbedarf = Math.round(myfloat);
        // else{}

        if (temp > 20 && temp < 30) {
            float myfloat = (float) wasserbedarf * 1.03f;
            wasserbedarf = Math.round(myfloat);
            altwasser = wasserbedarf;
        } else if (temp >= 30) {
            float myfloat = (float) wasserbedarf * 1.2f;
            wasserbedarf = Math.round(myfloat);
            altwasser = wasserbedarf;
        } else {
            altwasser = wasserbedarf;
        }
        progressanzeige();

    }

    @Override
    public void onBackPressed() {
        mSensorManager.unregisterListener(this);
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
            case R.id.nav_f: {
                Karte kartefragment = new Karte();
                RelativeLayout mainLayout = findViewById(R.id.mainLayout);
                mainLayout.removeAllViews();
                android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.mainLayout, kartefragment, kartefragment.getTag()).commit();

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

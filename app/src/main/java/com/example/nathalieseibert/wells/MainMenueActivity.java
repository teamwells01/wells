package com.example.nathalieseibert.wells;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import java.util.Calendar;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenueActivity extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener {

Button wasserButton, hackerlButton;
TextView mlview;
EditText mltext;
    private static final String DEBUG_TAG = "Tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menue);

        wasserButton = (Button) findViewById(R.id.addWater); //define Buttons for visibility
        hackerlButton = (Button) findViewById(R.id.haeckchen); //define Buttons for visibility
        mltext = (EditText) findViewById(R.id.editWater);//define edittext for visibility
        mlview = (TextView) findViewById(R.id.textMl) ;//define textview for visibility

        Switch simpleSwitch = findViewById(R.id.simpleSwitch); // initiate Switch
        simpleSwitch.setTextOn(getString(R.string.viel)); // displayed text of the Switch whenever it is in checked or on state
        simpleSwitch.setTextOff(getString(R.string.wenig)); // displayed text of the Switch whenever it is in unchecked i.e. off state

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

        findViewById(R.id.addWater).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 7);
                calendar.set(Calendar.MINUTE, 10);
                calendar.set(Calendar.SECOND, 0);

                Intent intent = new Intent(getApplicationContext(), MyNotificationPublisher.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                try {
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                }catch (NullPointerException e){
                    Toast.makeText(MainMenueActivity.this, getString(R.string.Error),
                            Toast.LENGTH_LONG).show();
                }

            }
        });

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


        return super.onOptionsItemSelected(item);


    }
//expandable menu - doesn´t work - don´t know why
    public void OnClickVisible(View view){

       // hackerlButton.setVisibility(View.VISIBLE);
        //mltext.setVisibility(View.VISIBLE);
        //mlview.setVisibility(View.VISIBLE);

    }

    public void OnClickHackerl(View view) {

       // hackerlButton.setVisibility(View.INVISIBLE);
        // mltext.setVisibility(View.INVISIBLE);
        //mlview.setVisibility(View.INVISIBLE);


        Log.d(DEBUG_TAG, "Some method called");
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
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}

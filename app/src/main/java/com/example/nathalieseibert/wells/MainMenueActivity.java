package com.example.nathalieseibert.wells;

import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import java.util.Calendar;

import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ImageView;

public class MainMenueActivity extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String DEBUG_TAG = "tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menue);


        Switch simpleSwitch = findViewById(R.id.simpleSwitch); // initiate Switch
        simpleSwitch.setTextOn("viel"); // displayed text of the Switch whenever it is in checked or on state
        simpleSwitch.setTextOff("wenig"); // displayed text of the Switch whenever it is in unchecked i.e. off state

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

    public void OnClickHackerl(View view) {
        Log.d(DEBUG_TAG, "Some method called");
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_f1) {
            Trinkbrunnen trinkbrunnenfragement = new Trinkbrunnen();
            RelativeLayout mainLayout = findViewById(R.id.mainLayout);
            mainLayout.removeAllViews();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.mainLayout, trinkbrunnenfragement, trinkbrunnenfragement.getTag()).commit();

        } else if (id == R.id.nav_f2) {
            Verlauf verlauffragment = new Verlauf();
            RelativeLayout mainLayout = findViewById(R.id.mainLayout);
            mainLayout.removeAllViews();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.mainLayout, verlauffragment, verlauffragment.getTag()).commit();


        } else if (id == R.id.nav_f3) {
            Statisitk statisitkfragment = new Statisitk();
            RelativeLayout mainLayout = findViewById(R.id.mainLayout);
            mainLayout.removeAllViews();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.mainLayout, statisitkfragment, statisitkfragment.getTag()).commit();

        } else if (id == R.id.nav_f4) {
            Einstellungen einstellungfragment = new Einstellungen();
            RelativeLayout mainLayout = findViewById(R.id.mainLayout);
            mainLayout.removeAllViews();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.mainLayout, einstellungfragment, einstellungfragment.getTag()).commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}

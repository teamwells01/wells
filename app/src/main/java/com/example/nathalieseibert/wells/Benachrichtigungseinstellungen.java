package com.example.nathalieseibert.wells;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Benachrichtigungseinstellungen extends Fragment {


    public Benachrichtigungseinstellungen() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        //Spinner die Werte zuteilen
        Spinner spinner;
        ArrayAdapter<CharSequence> adapter;
        View view = inflater.inflate(R.layout.fragment_benachrichtigungseinstellungen, container, false);
        spinner = view.findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.frequenz_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getActivity().getBaseContext(), parent.getItemAtPosition(position) + " ausgewählt", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(getActivity().getBaseContext(), "Nichts ausgewählt", Toast.LENGTH_SHORT).show();
            }
        });


        view.findViewById(R.id.speichernButon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 7);
                calendar.set(Calendar.MINUTE, 10);
                calendar.set(Calendar.SECOND, 0);

                Intent intent = new Intent(getActivity().getApplicationContext(), MyNotificationPublisher.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);

                try {
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                } catch (NullPointerException ignored) {

                }
            }


        });
        return view;


    }


}

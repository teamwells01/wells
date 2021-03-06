package at.fhjoanneum.gruppeWells.wells;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("ALL")
public class Benachrichtigungseinstellungen extends Fragment {
    private Spinner spinner;
    private Switch switchben;
    private EditText datum;
    private DatePickerDialog.OnDateSetListener date;
    private Calendar datumpicker;
    private EditText zeitpicker;
    private Spinner wspinner;
    private ArrayAdapter<CharSequence> adapter;
    private ArrayAdapter<CharSequence> adapterw;
    private Integer minuten;
    private Integer stunden;
    private int positionE;
    private int wochentag;
    private int monat;
    private Integer jahr;


    public Benachrichtigungseinstellungen() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        //Spinner die Werte zuteilen

        View view = inflater.inflate(R.layout.fragment_benachrichtigungseinstellungen, container, false);
        spinner = view.findViewById(R.id.spinner);
        switchben = view.findViewById(R.id.switchonben);
        zeitpicker = view.findViewById(R.id.time_view_edit);
        wspinner = view.findViewById(R.id.spinnerw);

        adapterw = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.wochentage, android.R.layout.simple_spinner_item);
        adapterw.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.frequenz_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        wspinner.setAdapter(adapterw);
        datumpicker = Calendar.getInstance();
        datum = view.findViewById(R.id.datum);
        datum.setVisibility(View.GONE);
        zeitpicker.setVisibility(View.GONE);
        wspinner.setVisibility(View.GONE);

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                datumpicker.set(Calendar.YEAR, year);
                datumpicker.set(Calendar.MONTH, monthOfYear);
                datumpicker.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
                wochentag = datumpicker.get(Calendar.DAY_OF_MONTH);
                monat = datumpicker.get(Calendar.MONTH);
                jahr = datumpicker.get(Calendar.YEAR);
            }

        };

        datum.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(getActivity(), date, datumpicker
                        .get(Calendar.YEAR), datumpicker.get(Calendar.MONTH),
                        datumpicker.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        zeitpicker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        zeitpicker.setText(selectedHour + ":" + selectedMinute);
                        minuten = selectedMinute;
                        stunden = selectedHour;
                        // TODO ALARM SETZEN
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positionE = position;
                switch (position) {
                    case 0:
                        datum.setVisibility(View.GONE);
                        wspinner.setVisibility(View.GONE);
                        zeitpicker.setVisibility(View.GONE);
                        break;
                    case 1:
                        datum.setVisibility(View.GONE);
                        wspinner.setVisibility(View.GONE);
                        zeitpicker.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        datum.setVisibility(View.GONE);
                        wspinner.setVisibility(View.VISIBLE);
                        zeitpicker.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        wspinner.setVisibility(View.GONE);
                        datum.setVisibility(View.VISIBLE);
                        zeitpicker.setVisibility(View.VISIBLE);
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity().getBaseContext(), "Nichts ausgewählt", Toast.LENGTH_SHORT).show();
            }
        });
        wspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        wochentag = 0;
                        break;
                    case 1:
                        wochentag = 1;
                        break;
                    case 2:
                        wochentag = 2;
                        break;
                    case 3:
                        wochentag = 3;
                        break;
                    case 4:
                        wochentag = 4;
                        break;
                    case 5:
                        wochentag = 5;
                        break;
                    case 6:
                        wochentag = 6;
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity().getBaseContext(), "Nichts ausgewählt", Toast.LENGTH_SHORT).show();
            }
        });


        view.findViewById(R.id.speichernButon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Das nächste sollte statt den addWater button auf den Benachrichtigung speichern Button bei den Einstellungen geändert werden!
                //Folgend wird täglich um 8:30 eine Notification erscheinen
                if (switchben.isChecked()) {
                    Calendar calendar = Calendar.getInstance();

                    if (positionE == 1) {
                        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.cancel(100);
                        if (minuten != null && stunden != null) {
                            calendar.set(Calendar.HOUR_OF_DAY, stunden);
                            calendar.set(Calendar.MINUTE, minuten);
                            calendar.set(Calendar.SECOND, 0);

                            Intent intent = new Intent(getActivity().getApplicationContext(), MyNotificationPublisher.class);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);

                            try {
                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                                Toast.makeText(getActivity(), "Alarm wurde um " + stunden + ":" + minuten + " gesetzt!", Toast.LENGTH_SHORT).show();
                            } catch (NullPointerException ignored) {
                                Toast.makeText(getActivity(), "Konnte Alarm nicht setzen!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Sie müssen eine Uhrzeit wählen!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    if (positionE == 0) {
                        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.cancel(100);
                        Intent intent = new Intent(getActivity().getApplicationContext(), MyNotificationPublisher.class);
                        PendingIntent alarmIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
                        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_HOUR,
                                AlarmManager.INTERVAL_HOUR, alarmIntent);
                        Toast.makeText(getActivity(), "Alarm wurde stündlich gesetzt!", Toast.LENGTH_SHORT).show();
                    }

                    if (positionE == 2) {
                        if (minuten != null && stunden != null) {
                            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                            notificationManager.cancel(100);
                            calendar.set(Calendar.DAY_OF_WEEK, wochentag);
                            calendar.set(Calendar.HOUR_OF_DAY, stunden);
                            calendar.set(Calendar.MINUTE, minuten);
                            calendar.set(Calendar.SECOND, 0);

                            Intent intent = new Intent(getActivity().getApplicationContext(), MyNotificationPublisher.class);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);

                            try {

                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);
                                Toast.makeText(getActivity(), "Alarm wurde am " + calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.GERMAN) + " um " + stunden + ":" + minuten + " gesetzt!", Toast.LENGTH_SHORT).show();
                            } catch (NullPointerException ignored) {
                                Toast.makeText(getActivity(), "Konnte Alarm nicht setzen!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Sie müssen eine Uhrzeit wählen!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    if (positionE == 3) {
                        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.cancel(100);
                        if (minuten != null && stunden != null) {
                            if (jahr != null) {
                                calendar.set(Calendar.YEAR, jahr);
                                calendar.set(Calendar.MONTH, monat);
                                calendar.set(Calendar.DAY_OF_WEEK, wochentag);
                                calendar.set(Calendar.HOUR_OF_DAY, stunden);
                                calendar.set(Calendar.MINUTE, minuten);
                                calendar.set(Calendar.SECOND, 0);

                                Intent intent = new Intent(getActivity().getApplicationContext(), MyNotificationPublisher.class);
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);

                                try {
                                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                                    Toast.makeText(getActivity(), "Alarm wurde am " + calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.GERMAN) + " den " + wochentag + ". " + calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.GERMAN) + " um " + stunden + ":" + minuten + " gesetzt!", Toast.LENGTH_SHORT).show();
                                } catch (NullPointerException ignored) {
                                    Toast.makeText(getActivity(), "Konnte Alarm nicht setzen!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Sie müssen einen Tag wählen!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Sie müssen eine Uhrzeit wählen!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.cancel(100);
                    Toast.makeText(getActivity(), "Benachrichtigungen ausgeschaltet!", Toast.LENGTH_SHORT).show();
                }


            }


        });

        return view;
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMAN);

        datum.setText(sdf.format(datumpicker.getTime()));

        // TODO ALARM SETZEN
    }


}

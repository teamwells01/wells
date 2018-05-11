package com.example.nathalieseibert.wells;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 */
public class Einstellungen extends Fragment {


    public Einstellungen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_einstellungen, container, false);

        Button Grundeinstellungen = (Button) view.findViewById(R.id.Grundeinstellungen);
        Grundeinstellungen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grund_einstellung grund_einstellungfragment = new grund_einstellung();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.mainLayout, grund_einstellungfragment, grund_einstellungfragment.getTag()).commit();
            }
        });

        Button Benachrichtigung = (Button) view.findViewById(R.id.Benachrichtigung);
        Benachrichtigung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Benachrichtigungseinstellungen benachrichtigunggfragment = new Benachrichtigungseinstellungen();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.mainLayout, benachrichtigunggfragment, benachrichtigunggfragment.getTag()).commit();
            }
        });

        Button PWAendern = (Button) view.findViewById(R.id.PWAendern);
        PWAendern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PWaendern pwaendernfragement = new PWaendern();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.mainLayout, pwaendernfragement, pwaendernfragement.getTag()).commit();
            }
        });
        return view;


    }

}

package com.example.nathalieseibert.wells;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Verlauf extends Fragment {


    public Verlauf() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_verlauf2, container, false);


        Button buttonkarte = view.findViewById(R.id.buttonkarte);
        buttonkarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Statisitk statisitkfragment = new Statisitk();
                FragmentManager manager = getFragmentManager();
                try {
                    manager.beginTransaction().replace(R.id.mainLayout, statisitkfragment, statisitkfragment.getTag()).commit();
                } catch (NullPointerException e) {
                    Toast.makeText(getActivity(), "Error",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;

    }


}

package com.example.nathalieseibert.wells;


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
public class Statisitk extends Fragment {


    public Statisitk() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statisitk, container, false);

        Button buttonlist = (Button) view.findViewById(R.id.buttonlist);
        buttonlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Verlauf verlauffragment = new Verlauf();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.mainLayout, verlauffragment, verlauffragment.getTag()).commit();
            }
        });
        return view;
    }


}

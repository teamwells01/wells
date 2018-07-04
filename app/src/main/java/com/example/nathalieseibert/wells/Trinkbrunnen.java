package com.example.nathalieseibert.wells;


import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Trinkbrunnen extends Fragment {

DatabaseHelper myDB;
    private ArrayList<String> results = new ArrayList<String>();
    private String tableName = myDB.LIST_TABLE;
    private SQLiteDatabase newDB;

    public Trinkbrunnen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trinkbrunnen, container, false);




        try {
            myDB = new DatabaseHelper(getContext());
           // myDB = DatabaseHelper.getWritableDatabase();
            Cursor c = myDB.getAllData();
            String output;

            if (c != null ) {
                if (c.moveToFirst()) {
                    do {
                        output = c.getString(0);

                        results.add(output);
                    }while (c.moveToNext());
                    ListView brunnenView =(ListView) view.findViewById(R.id.brunnenview);

                    ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(
                            getActivity(),
                            android.R.layout.simple_expandable_list_item_1,results);

                    brunnenView.setAdapter(listAdapter);
                }
            }
        } catch (SQLiteException se ) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }

//
//        TextView tView = new TextView(getContext());
//        tView.setText("This data is retrieved from the database and only 4 " +
//                "of the results are displayed");
//        getListView().addHeaderView(tView);
//
//        setListAdapter(new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, results));
//        getListView().setTextFilterEnabled(true);
//

//
//
//        if(res.getCount() ==0){
//showMessage("Error", "Nothing found!");
//        }
//
//        StringBuffer buffer = new StringBuffer();
//        while(res.moveToNext()){
//            buffer.append(res.getString(0) + "\n");
//        }
//    showMessage("Data", buffer.toString());
//




        Button buttonkarte = view.findViewById(R.id.buttonkarte);
        buttonkarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Karte kartefragment = new Karte();
                FragmentManager manager = getFragmentManager();
                try {
                    manager.beginTransaction().replace(R.id.mainLayout, kartefragment, kartefragment.getTag()).commit();
                } catch (NullPointerException e) {
                    Toast.makeText(getActivity(), "Error",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    public void showMessage (String title, String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
    }

}

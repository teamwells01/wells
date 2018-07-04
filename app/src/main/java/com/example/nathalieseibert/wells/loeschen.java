package com.example.nathalieseibert.wells;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class loeschen extends Fragment {

    Button button_delete;
    DatabaseHelper myDB;
    EditText editpass;
    AutoCompleteTextView editmail;

    public loeschen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loeschen, container, false);


        button_delete = (Button) view.findViewById(R.id.delete_button);
        myDB = new DatabaseHelper(getContext());
        editmail = (AutoCompleteTextView) view.findViewById(R.id.email);
        editpass = (EditText) view.findViewById(R.id.password);
//delete data via databasehelper

        button_delete.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 String email = editmail.getText().toString();
                                                 String pass = editpass.getText().toString();


                                                 Integer deleteRowFKListe = myDB.deleteFroeignKeyDataListe(email);
                                                 Integer deleteRowFKTrinken = myDB.deleteFroeignKeyDataTrinken(email);
                                                 Integer deletedRows = myDB.deleteData(email, pass);

                                                 if (deleteRowFKTrinken >= 0 && deleteRowFKListe >= 0) {

                                                     if (deletedRows > 0) {
                                                         Toast.makeText(getContext(), "Data deleted", Toast.LENGTH_LONG).show();
                                                     } else {
                                                         Toast.makeText(getContext(), "Data not deleted", Toast.LENGTH_LONG).show();
                                                     }
                                                 }
                                             }
                                         }
        );

        // Inflate the layout for this fragment
        return view;

    }


}

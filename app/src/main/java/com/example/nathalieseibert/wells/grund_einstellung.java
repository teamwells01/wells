package com.example.nathalieseibert.wells;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class grund_einstellung extends Fragment {
    DatabaseHelper openHelper;
    Button _buttonupdate;
    EditText _editTextEmail;
    EditText _editTextAge;
    EditText _editTextWeight;
    EditText _editTextHeight;


    public grund_einstellung() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_grund_einstellung, container, false);

        openHelper = new DatabaseHelper(getContext()); //?????
        _buttonupdate = (Button) view.findViewById(R.id.button_update);
        _editTextEmail = (EditText) view.findViewById(R.id.email_update);
        _editTextAge = (EditText) view.findViewById(R.id.age_update);
        _editTextWeight = (EditText) view.findViewById(R.id.weight_update);
        _editTextHeight = (EditText) view.findViewById(R.id.height_update);


        //public void UpdateData(){ //wo soll de aufgerufen werden???? -> im video wird sie bei "mainMenueActivity" aufgerufen
        _buttonupdate.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 String email = _editTextEmail.getText().toString();
                                                 String age = _editTextAge.getText().toString();
                                                 String weight = _editTextWeight.getText().toString();
                                                 String height = _editTextHeight.getText().toString();

                                                 boolean isUpdate = openHelper.updateData(email, age, weight, height);

                                                 if (isUpdate == true) {
                                                     Toast.makeText(getContext(), "Updated successfully", Toast.LENGTH_SHORT).show();
                                                 } else {
                                                     Toast.makeText(getContext(), "Update was not successfully", Toast.LENGTH_SHORT).show();
                                                 }
                                             }
                                         }
        );
        //}
        return view;


    }
}

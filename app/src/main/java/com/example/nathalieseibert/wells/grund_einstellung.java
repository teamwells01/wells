package com.example.nathalieseibert.wells;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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

        openHelper = new DatabaseHelper(getContext()); //
        _buttonupdate = view.findViewById(R.id.button_update);
        _editTextEmail = view.findViewById(R.id.email_update);
        _editTextAge = view.findViewById(R.id.age_update);
        _editTextWeight = view.findViewById(R.id.weight_update);
        _editTextHeight = view.findViewById(R.id.height_update);


        _buttonupdate.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 String email = _editTextEmail.getText().toString();
                                                 String age = _editTextAge.getText().toString();
                                                 String weight = _editTextWeight.getText().toString();
                                                 String height = _editTextHeight.getText().toString();

                                                 String mail = getActivity().getIntent().getStringExtra("Email"); //für EMail überpüfen

                                                 if (email.equals("") || age.equals("") || weight.equals("") || height.equals("")) {
                                                     Toast.makeText(getContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                                                 } else {
                                                     if (mail.equals(email)) { //stimmt das?
                                                         boolean isUpdate = openHelper.updateData(email, age, weight, height);
                                                         if (isUpdate == true) {
                                                             Toast.makeText(getContext(), "Updated successfully", Toast.LENGTH_SHORT).show();
                                                         } else {
                                                             Toast.makeText(getContext(), "Update was not successfully", Toast.LENGTH_SHORT).show();
                                                         }
                                                     } else {
                                                         Toast.makeText(getContext(), "Wrong email", Toast.LENGTH_LONG).show();
                                                     }
                                                 }


                                             }
                                         }
        );
        return view;


    }
}

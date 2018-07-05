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
public class PWaendern extends Fragment {
    DatabaseHelper openHelper;
    Button _pwSpeichernButton;
    EditText _newPw_conform;
    EditText _newPw_update;
    EditText _oldPw_update;
    EditText _email_update;

    public PWaendern() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pwaendern, container, false);

        openHelper = new DatabaseHelper(getContext()); //
        _pwSpeichernButton = view.findViewById(R.id.pwSpeichernButton);
        _newPw_conform = view.findViewById(R.id.newPw_conform);
        _newPw_update = view.findViewById(R.id.newPw_update);
        _oldPw_update = view.findViewById(R.id.oldPw_update);
        _email_update = view.findViewById(R.id.email_update);


        _pwSpeichernButton.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View v) {
                                                      String newPW = _newPw_conform.getText().toString();
                                                      String PWupdate = _newPw_update.getText().toString();
                                                      String oldPW = _oldPw_update.getText().toString();
                                                      String email = _email_update.getText().toString();
                                                      String mail = getActivity().getIntent().getStringExtra("Email"); //für EMail überpüfen

                                                      //neues intent erstellen??
                                                      //String pass = getActivity().getIntent().getStringExtra("Passwort");

                                                      if (email.equals("") || newPW.equals("") || PWupdate.equals("") || oldPW.equals("")) {
                                                          Toast.makeText(getContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                                                      } else {
                                                          if (mail.equals(email)) { //pass.equals(oldPW) && //passwort überprüfen fehlt mir noch
                                                              if (openHelper.checkpass(email, oldPW)) {
                                                                  if (newPW.equals(PWupdate)) {
                                                                      boolean isUpdate = openHelper.PWupdate(email, newPW);
                                                                      if (isUpdate == true) {
                                                                          Toast.makeText(getContext(), "Updated successfully", Toast.LENGTH_SHORT).show();
                                                                      } else {
                                                                          Toast.makeText(getContext(), "Update was not successfully", Toast.LENGTH_SHORT).show();
                                                                      }
                                                                  } else {
                                                                      Toast.makeText(getContext(), "Password new and password confirmed are not equal!", Toast.LENGTH_LONG).show();
                                                                  }

                                                              } else {
                                                                  Toast.makeText(getContext(), "Wrong old password!", Toast.LENGTH_LONG).show();
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

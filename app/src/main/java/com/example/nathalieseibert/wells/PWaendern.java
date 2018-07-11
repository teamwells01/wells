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
    private DatabaseHelper openHelper;
    private EditText _newPw_conform;
    private EditText _newPw_update;
    private EditText _oldPw_update;
    private EditText _email_update;

    public PWaendern() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pwaendern, container, false);

        openHelper = new DatabaseHelper(getContext()); //
        Button _pwSpeichernButton = view.findViewById(R.id.pwSpeichernButton);
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


                                                      try {

                                                          if (email.equals("") || newPW.equals("") || PWupdate.equals("") || oldPW.equals("")) {
                                                              Toast.makeText(getContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                                                          } else {
                                                              if (mail.equals(email)) {
                                                                  if (openHelper.checkpass(email, oldPW)) {
                                                                      if (newPW.equals(PWupdate)) {
                                                                          boolean isUpdate = openHelper.PWupdate(email, newPW);
                                                                          if (isUpdate) {
                                                                              Toast.makeText(getContext(), "Updated successfully", Toast.LENGTH_SHORT).show();
                                                                          } else {
                                                                              Toast.makeText(getContext(), "Update was not successfully", Toast.LENGTH_SHORT).show();
                                                                          }
                                                                      } else {
                                                                          Toast.makeText(getContext(), "Password new and password confirmed are not equal!", Toast.LENGTH_LONG).show();
                                                                      }

                                                                  } else {
                                                                      Toast.makeText(getContext(), "Wrong old password!", Toast.LENGTH_SHORT).show();
                                                                  }
                                                              } else {
                                                                  Toast.makeText(getContext(), "Wrong email", Toast.LENGTH_SHORT).show();
                                                              }
                                                          }

                                                      }catch (Exception e) {
                                                              Toast.makeText(getContext(), "Achtung! Bei Anmeldung via Fingerprint ist eine Änderung des Passwortes nicht möglich! Es wird eine eMail benötigt!", Toast.LENGTH_LONG).show();
                                                          }
                                                  }
                                              }
        );
        return view;
    }

}

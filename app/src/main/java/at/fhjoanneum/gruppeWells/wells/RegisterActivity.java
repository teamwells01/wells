package at.fhjoanneum.gruppeWells.wells;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private DatabaseHelper openHelper;
    private EditText _editTextEmail;
    private EditText _editTextPassword;
    private EditText _editTextName;
    private EditText _editTextAge;
    private EditText _editTextWeight;
    private EditText _editTextHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        openHelper = new DatabaseHelper(this);
        Button _action_register_in = findViewById(R.id.action_register_in);
        _editTextEmail = findViewById(R.id.editTextEmail);
        _editTextPassword = findViewById(R.id.editTextPassword);
        _editTextName = findViewById(R.id.editTextName);
        _editTextAge = findViewById(R.id.editTextAge);
        _editTextWeight = findViewById(R.id.editTextWeight);
        _editTextHeight = findViewById(R.id.editTextHeight);

        _action_register_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = _editTextEmail.getText().toString();
                String pass = _editTextPassword.getText().toString();
                String name = _editTextName.getText().toString();
                String age = _editTextAge.getText().toString();
                String weight = _editTextWeight.getText().toString();
                String height = _editTextHeight.getText().toString();

                if (isEmailValid(email)) {
                    return;
                }

                if (isPasswordValid(pass)) {
                    return;
                }

                if (email.equals("") || pass.equals("") || name.equals("") || age.equals("") || weight.equals("") || height.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();

                } else {
                    Boolean checkmail = openHelper.checkmail(email);
                    if (checkmail) {
                        Boolean insertdata = openHelper.insertdata(email, pass, name, age, weight, height);
                        if (insertdata) {
                            Toast.makeText(getApplicationContext(), "Registered successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "EMail already exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean isEmailValid(String email) {
        EditText emailFeld = findViewById(R.id.editTextEmail);
        String eingabe = emailFeld.getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(eingabe).matches()) {
            emailFeld.setError("Bitte geben Sie eine gültige Email ein!");
            emailFeld.setHintTextColor(getResources().getColor(R.color.red));
            return true;
        } else {
            return false;
        }

    }

    private boolean isPasswordValid(String password) {
        EditText passFeld = findViewById(R.id.editTextPassword);


        if (password.length() >= 4) {
            return false;
        } else {
            passFeld.setError("Das Passwort muss vier Zeichen lang sein!");
            passFeld.setHintTextColor(getResources().getColor(R.color.red));
        }

        return true;
    }


}

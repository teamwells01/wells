//package com.example.nathalieseibert.wells;
//
//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//
//public class RegisterActivity extends AppCompatActivity {
//
//    public void onClickSwitchActivity(View view) {
//        Intent intent = new Intent(this, MainMenueActivity.class);
//        startActivity(intent);
//
//     //  Intent i = new Intent(this, IntentService.class);
//      //  startService(i);
//
//    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//    }
//}

package com.example.nathalieseibert.wells;

        import android.content.ContentValues;
        import android.content.Intent;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    DatabaseHelper openHelper;
    SQLiteDatabase db;
    Button _action_register_in;
    EditText _editTextEmail;
    EditText _editTextPassword;
    EditText _editTextName;
    EditText _editTextAge;
    EditText _editTextWeight;
    EditText _editTextHeight;


    public void onClickSwitchActivity(View view) {
        Intent intent = new Intent(this, MainMenueActivity.class);
        startActivity(intent);

        //  Intent i = new Intent(this, IntentService.class);
        //  startService(i);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        openHelper = new DatabaseHelper(this);
        _action_register_in = (Button)findViewById(R.id.action_register_in);
        _editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        _editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        _editTextName = (EditText)findViewById(R.id.editTextName);
        _editTextAge = (EditText)findViewById(R.id.editTextAge);
        _editTextWeight = (EditText)findViewById(R.id.editTextWeight);
        _editTextHeight = (EditText)findViewById(R.id.editTextHeight);

        _action_register_in.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email = _editTextEmail.getText().toString();
                String pass = _editTextPassword.getText().toString();
                String name = _editTextName.getText().toString();
                String age = _editTextAge.getText().toString();
                String weight = _editTextWeight.getText().toString();
                String height = _editTextHeight.getText().toString();

                if(email.equals("")||pass.equals("")||name.equals("")||age.equals("")||weight.equals("")||height.equals("")){
                    Toast.makeText(getApplicationContext(),"Fields are empty",Toast.LENGTH_SHORT).show();

                }else{
                    Boolean checkmail = openHelper.checkmail(email);
                    if(checkmail == true){
                        Boolean insertdata = openHelper.insertdata(email, pass, name, age, weight, height);
                        if(insertdata == true){
                            Toast.makeText(getApplicationContext(),"Registered successfully",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"EMail already exists",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

}

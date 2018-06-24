package com.example.nathalieseibert.wells;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class RegisterActivity extends AppCompatActivity {

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
    }
}

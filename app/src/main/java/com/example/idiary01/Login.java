package com.example.idiary01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    EditText password;
    Button enter;
    Button recover;
    String realPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        password = (EditText) findViewById(R.id.enterPass);
        enter = (Button) findViewById(R.id.button2);
        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        realPassword = settings.getString("password", "");


        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = password.getText().toString();
                if(pass.equals(realPassword)){
                    startActivity(new Intent(Login.this, diaryView.class));
                    finish();
                }
            }
        });

        recover = (Button) findViewById(R.id.button3);

        recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, passRecovery.class));
                finish();
            }
        });

    }
}
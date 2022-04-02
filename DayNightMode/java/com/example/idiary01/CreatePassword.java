package com.example.idiary01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreatePassword extends AppCompatActivity {

    EditText newPass, confirmPass;
    Button save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);

        newPass = (EditText) findViewById(R.id.enterPassword);
        confirmPass = (EditText) findViewById(R.id.confirmPassword);
        save = (Button) findViewById(R.id.button);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass1 = newPass.getText().toString();
                String pass2 = confirmPass.getText().toString();

                if(pass1.equals("") || pass2.equals("")){

                }
                else{
                    if(pass1.equals(pass2)){
                        SharedPreferences settings = getSharedPreferences("PREFS", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("password", pass1);
                        editor.apply();

                        startActivity(new Intent(CreatePassword.this, createSecurityQuestion.class));
                        finish();
                    }
                }
            }
        });
    }
}
package com.example.idiary01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ChangePassword extends AppCompatActivity {

    EditText oldPass, newPass, confirmPass;
    Button save, returner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        oldPass = (EditText) findViewById(R.id.editTextPasswordOld);
        newPass = (EditText) findViewById(R.id.editTextPasswordNew);
        confirmPass = (EditText) findViewById(R.id.editTextPasswordConfirm);
        save = (Button) findViewById(R.id.button12);
        returner = (Button) findViewById(R.id.button13);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldePass = oldPass.getText().toString();
                String newwPass = newPass.getText().toString();
                String confirmmPass = confirmPass.getText().toString();
                SharedPreferences settings = getSharedPreferences("PREFS", 0);
                String realPassword = settings.getString("password", "");



                if(oldePass.equals("") || newwPass.equals("") || confirmmPass.equals("")){

                }
                else{
                    if(oldePass.equals(realPassword)){

                        if(newwPass.equals(confirmmPass)) {
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("password", newwPass);
                            editor.apply();

                            startActivity(new Intent(ChangePassword.this, diaryView.class));
                            finish();
                        }
                    }
                }
            }
        });

        returner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), settingsPage.class));
                finish();
            }
        });
    }
}
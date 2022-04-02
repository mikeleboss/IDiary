package com.example.idiary01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class settingsPage extends AppCompatActivity {

    Button returner;
    Button passChange;
    Button quesChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        returner = (Button) findViewById(R.id.button9);
        passChange = (Button) findViewById(R.id.button10);
        quesChange = (Button) findViewById(R.id.button11);

        returner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), diaryView.class));
                finish();
            }
        });

        quesChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ChangeQuestion.class));
                finish();
            }
        });
        passChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ChangePassword.class));
                finish();
            }
        });
    }
}
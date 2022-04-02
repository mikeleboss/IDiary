package com.example.idiary01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class passRecovery extends AppCompatActivity {

    EditText answer;
    TextView question;
    TextView password;
    Button enter;
    Button returner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_recovery);

        answer = (EditText) findViewById(R.id.editTextAnswer1);
        question = (TextView) findViewById(R.id.textViewQuestion);
        password = (TextView) findViewById(R.id.textViewpassword);
        enter = (Button) findViewById(R.id.button5);
        returner = (Button) findViewById(R.id.button6);
        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        String questionstring = settings.getString("question", "");
        question.setText(questionstring);
        String answerreal = settings.getString("answer", "");
        String passwordstring = settings.getString("password","");

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answer.getText().toString().equals(answerreal)){
                    password.setText(passwordstring);
                }
            }
        });
        returner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(passRecovery.this, Login.class));
                finish();

            }
        });





    }
}
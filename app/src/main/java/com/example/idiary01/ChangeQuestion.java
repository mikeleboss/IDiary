package com.example.idiary01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ChangeQuestion extends AppCompatActivity {

    Button save, returner;
    EditText passwordBox, newQuestion, newAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_question);

        save = (Button) findViewById(R.id.button14);
        returner = (Button) findViewById(R.id.button15);

        passwordBox = (EditText) findViewById(R.id.editTextPassword);
        newQuestion = (EditText) findViewById(R.id.editTextQuestionNew);
        newAnswer = (EditText) findViewById(R.id.editTextAnswerNew);



        returner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), settingsPage.class));
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences settings = getSharedPreferences("PREFS", 0);
                String realPassword = settings.getString("password", "");
                String pass = passwordBox.getText().toString();
                String ques  = newQuestion.getText().toString();
                String ans = newAnswer.getText().toString();


                if(pass.equals(realPassword)){
                    if(!ans.equals("") && !ques.equals("")) {
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("question", ques);
                        editor.putString("answer", ans);
                        editor.apply();

                        startActivity(new Intent(getApplicationContext(), diaryView.class));
                        finish();
                    }
                }
            }
        });
    }
}
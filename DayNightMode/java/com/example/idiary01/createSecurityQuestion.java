package com.example.idiary01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class createSecurityQuestion extends AppCompatActivity {

    EditText question, answer;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_security_question);

        answer = (EditText) findViewById(R.id.editTextAnswer);
        question = (EditText) findViewById(R.id.editTextQuestion);
        save = (Button) findViewById(R.id.button4);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answerstring = answer.getText().toString();
                String questionstring = question.getText().toString();



                SharedPreferences settings = getSharedPreferences("PREFS", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("question", questionstring);
                editor.putString("answer", answerstring);
                editor.apply();

                startActivity(new Intent(createSecurityQuestion.this, diaryView.class));
                finish();


            }
        });

    }


}
package com.example.idiary01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashSet;

public class NoteEditor extends AppCompatActivity {


    String entryID, noteID;
    EditText editTextEntryText;
    Button returner, save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        returner = (Button) findViewById(R.id.button19);
        save = (Button) findViewById(R.id.button20);
        editTextEntryText = (EditText) findViewById(R.id.editTextEntryText);

        Intent intent = getIntent();
        entryID = intent.getStringExtra("entryID");
        noteID = intent.getStringExtra("noteID");
        editTextEntryText.setText(settings.getString(entryID, ""));

        returner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intents = new Intent(getApplicationContext(), entryView.class);
                intents.putExtra("noteID", noteID);
                startActivity(intents);
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String entry = editTextEntryText.getText().toString();
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(entryID, entry);
                editor.apply();
            }
        });

    }
}
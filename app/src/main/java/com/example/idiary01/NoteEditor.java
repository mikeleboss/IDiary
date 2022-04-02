package com.example.idiary01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.BuildCompat;
import androidx.core.view.inputmethod.EditorInfoCompat;
import androidx.core.view.inputmethod.InputConnectionCompat;
import androidx.core.view.inputmethod.InputContentInfoCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashSet;

public class NoteEditor extends AppCompatActivity {


    String entryID, noteID;
    EditText editTextEntryText;
    Button returner, save, editColor, editFont;
    int color = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        returner = (Button) findViewById(R.id.button19);
        save = (Button) findViewById(R.id.button20);
        editColor = (Button) findViewById(R.id.button26);
        editFont = (Button) findViewById(R.id.button27);
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
        editColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(color == 1) {
                    editTextEntryText.setTextColor(0xffBB86FC);
                    color++;
                }
                else if(color == 2) {
                    editTextEntryText.setTextColor(0xff6200EE);
                    color++;
                }
                else if(color == 3) {
                    editTextEntryText.setTextColor(0xff03DAC5);
                    color++;
                }
               else if(color == 4) {
                    editTextEntryText.setTextColor(0xffC00000);
                    color++;
                }
                else if(color == 5) {
                    editTextEntryText.setTextColor(0xff2450a4);
                    color++;
                }
                else if(color == 6) {
                    editTextEntryText.setTextColor(0xff228b22);
                    color++;
                }
                else{
                    editTextEntryText.setTextColor(0xff000000);
                    color = 1;
                }
            }
        });

        editFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextEntryText.setTypeface(Typeface.SANS_SERIF);
            }
        });



    }
}
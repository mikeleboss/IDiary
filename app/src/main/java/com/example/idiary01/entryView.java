package com.example.idiary01;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;


public class entryView extends AppCompatActivity {

    String noteID;
    static ArrayList<String> entries = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;
    Button returner, addEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_view);

        Intent intent = getIntent();
        noteID = intent.getStringExtra("noteID");

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("PREFS", 0);
        HashSet<String> set = (HashSet<String>)sharedPreferences.getStringSet(noteID, null);
        ListView listView = (ListView) findViewById(R.id.listViewEntries);
        returner = (Button) findViewById(R.id.button18);
        addEntry = (Button) findViewById(R.id.button17);

        if(set == null) {
            entries.add("exampleEntry");
            set = new HashSet<>(entryView.entries);
            sharedPreferences.edit().putStringSet(noteID, set).apply();
        }
        else{
            entries = new ArrayList<String>(set);
        }

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, entries);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String entryID = noteID + "." + entries.get(position);
                Intent intents = new Intent(getApplicationContext(), NoteEditor.class);
                intents.putExtra("entryID", entryID);

                startActivity(intents);
                finish();
            }
        });

        returner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), diaryView.class));
                finish();
            }
        });

        addEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText title = new EditText(entryView.this);
                new AlertDialog.Builder(entryView.this)                   // we can't use getApplicationContext() here as we want the activity to be the context, not the application
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Add Entry")
                        .setView(title)
                        .setMessage("Are you sure you want to add this entry?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)                        // to remove the selected note once "Yes" is pressed
                            {
                                if(!title.getText().toString().equals("")) {
                                    entries.add(title.getText().toString());
                                    arrayAdapter.notifyDataSetChanged();


                                    HashSet<String> set = new HashSet<>(entryView.entries);
                                    sharedPreferences.edit().putStringSet(noteID, set).apply();
                                }
                            }
                        })

                        .setNegativeButton("No", null)
                        .show();
            }


        });

    }
}
package com.example.idiary01;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class diaryView extends AppCompatActivity {


    static ArrayList<String> diaries = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;
    Button settings;
    Button addDiary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_view);

        Button settings = (Button) findViewById(R.id.button7);
        Button addDiary = (Button) findViewById(R.id.button8);
        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayList<String> exampleDiary = new ArrayList<String>();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("PREFS", 0);

        HashSet<String> set = (HashSet<String>)sharedPreferences.getStringSet("diaries", null);

        if(set == null) {
            diaries.add("exampleDiary");
        }
        else{
            diaries = new ArrayList<>(set);
        }




        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, diaries);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(getApplicationContext(), entryView.class);
                intent.putExtra("noteID", position);
                startActivity(intent);
                finish();
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(diaryView.this, settingsPage.class));
                finish();
            }
        });
        addDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText title = new EditText(diaryView.this);
                new AlertDialog.Builder(diaryView.this)                   // we can't use getApplicationContext() here as we want the activity to be the context, not the application
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Add Diary")
                        .setView(title)
                        .setMessage("Are you sure you want to create this diary?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)                        // to remove the selected note once "Yes" is pressed
                            {
                                diaries.add(title.getText().toString());
                                arrayAdapter.notifyDataSetChanged();

                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("PREFS", 0);
                                HashSet<String> set = new HashSet<>(diaryView.diaries);
                                sharedPreferences.edit().putStringSet("diaries", set).apply();
                            }
                        })

                        .setNegativeButton("No", null)
                        .show();
            }


        });

    }
}
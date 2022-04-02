package com.example.idiary01;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
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
    Button settings, addDiary, reminders;
    EditText searchBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_view);

        settings = (Button) findViewById(R.id.button7);
        addDiary = (Button) findViewById(R.id.button8);
        reminders = (Button) findViewById(R.id.button16);
        searchBar = (EditText) findViewById(R.id.editTextDiarySearch);
        ListView listView = (ListView) findViewById(R.id.listView);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("PREFS", 0);

        HashSet<String> set = (HashSet<String>)sharedPreferences.getStringSet("diaries", null);

        if(set == null) {
            diaries.add("exampleDiary");
            set = new HashSet<>(diaryView.diaries);
            sharedPreferences.edit().putStringSet("diaries", set).apply();
        }
        else{
            diaries = new ArrayList<String>(set);
        }




        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, diaries);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(getApplicationContext(), entryView.class);
                intent.putExtra("noteID", diaries.get(position));
                startActivity(intent);
                finish();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id)
            {
                new AlertDialog.Builder(diaryView.this)                   // we can't use getApplicationContext() here as we want the activity to be the context, not the application
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete?")
                        .setMessage("Are you sure you want to delete this diary?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)                        // to remove the selected note once "Yes" is pressed
                            {
                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("PREFS", 0);
                                HashSet<String> entryDeleteSet = (HashSet<String>) sharedPreferences.getStringSet(diaries.get(position), null);
                                ArrayList<String> entryDeleteList = new ArrayList<String>(entryDeleteSet);
                                for(String s: entryDeleteList){
                                    String entryID = diaries.get(position) + "." + s;
                                    sharedPreferences.edit().remove(entryID).commit();
                                }
                                sharedPreferences.edit().remove(diaries.get(position)).commit();


                                diaries.remove(position);
                                arrayAdapter.notifyDataSetChanged();
                                HashSet<String> set = new HashSet<>(diaryView.diaries);
                                sharedPreferences.edit().putStringSet("diaries", set).apply();


                            }
                        })

                        .setNegativeButton("No", null)
                        .show();

                return true;               // this was initially false but we change it to true as if false, the method assumes that we want to do a short click after the long click as well
            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                arrayAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(diaryView.this, settingsPage.class));
                finish();
            }
        });

        reminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(diaryView.this, Reminders.class));
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
                        .setMessage("Name the new diary")
                        .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)                        // to remove the selected note once "Yes" is pressed
                            {
                                if(!title.getText().toString().equals("")) {
                                    diaries.add(title.getText().toString());
                                    arrayAdapter.notifyDataSetChanged();


                                    HashSet<String> set = new HashSet<>(diaryView.diaries);
                                    sharedPreferences.edit().putStringSet("diaries", set).apply();
                                }
                            }
                        })

                        .setNegativeButton("cancel", null)
                        .show();
            }


        });

    }
}
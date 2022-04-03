package com.example.idiary01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.os.BuildCompat;
import androidx.core.view.inputmethod.EditorInfoCompat;
import androidx.core.view.inputmethod.InputConnectionCompat;
import androidx.core.view.inputmethod.InputContentInfoCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.HashSet;

public class NoteEditor extends AppCompatActivity {


    String entryID, noteID, imageURI;
    EditText editTextEntryText;
    Button returner, save, editColor, editFont, picture;
    int color, font;
    ImageView image;
    private static int RESULT_LOAD_IMAGE = 3;
    Bitmap bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        returner = (Button) findViewById(R.id.button19);
        save = (Button) findViewById(R.id.button20);
        editColor = (Button) findViewById(R.id.button26);
        editFont = (Button) findViewById(R.id.button27);
        picture = (Button) findViewById(R.id.uploadImage);
        image = (ImageView) findViewById(R.id.imageToUpload);

        editTextEntryText = (EditText) findViewById(R.id.editTextEntryText);



        Intent intent = getIntent();
        entryID = intent.getStringExtra("entryID");
        noteID = intent.getStringExtra("noteID");
        String colorID = entryID + ".color";
        String picID = entryID + ".picture";
        String fontID = entryID +".font";
        font = settings.getInt(fontID, 3);
        color = settings.getInt(colorID, 7);
        imageURI = settings.getString(picID, null);
        editTextEntryText.setText(settings.getString(entryID, ""));

        if(color == 1) {
            editTextEntryText.setTextColor(0xffBB86FC);

        }
        else if(color == 2) {
            editTextEntryText.setTextColor(0xff6200EE);

        }
        else if(color == 3) {
            editTextEntryText.setTextColor(0xff03DAC5);

        }
        else if(color == 4) {
            editTextEntryText.setTextColor(0xffC00000);

        }
        else if(color == 5) {
            editTextEntryText.setTextColor(0xff2450a4);

        }
        else if(color == 6) {
            editTextEntryText.setTextColor(0xff228b22);

        }
        else{
            editTextEntryText.setTextColor(0xff000000);

        }

        if(font == 1) {
            editTextEntryText.setTypeface(Typeface.DEFAULT_BOLD);

        }
        else if(font == 2) {
            editTextEntryText.setTypeface(Typeface.SERIF);

        }
        else if(font == 3) {
            editTextEntryText.setTypeface(Typeface.DEFAULT);

        }


        image.setImageBitmap(StringToBitMap(imageURI));

        picture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);


            }
        });

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
                editor.putInt(colorID, color);
                editor.putInt(fontID, font);
                editor.putString(picID, BitMapToString(bm));
                editor.apply();
            }
        });


        editColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if(color < 7) {
                    color++;
                }
                else{
                    color = 1;
                }

                if(color == 1) {
                    editTextEntryText.setTextColor(0xffBB86FC);

                }
                else if(color == 2) {
                    editTextEntryText.setTextColor(0xff6200EE);

                }
                else if(color == 3) {
                    editTextEntryText.setTextColor(0xff03DAC5);

                }
                else if(color == 4) {
                    editTextEntryText.setTextColor(0xffC00000);

                }
                else if(color == 5) {
                    editTextEntryText.setTextColor(0xff2450a4);

                }
                else if(color == 6) {
                    editTextEntryText.setTextColor(0xff228b22);

                }
                else{
                    editTextEntryText.setTextColor(0xff000000);

                }
            }

        });

        editFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(font < 3){
                    font++;
                }
                else{
                    font = 1;
                }

                if(font == 1) {
                    editTextEntryText.setTypeface(Typeface.DEFAULT_BOLD);

                }
                else if(font == 2) {
                    editTextEntryText.setTypeface(Typeface.SERIF);

                }
                else if(font == 3) {
                    editTextEntryText.setTypeface(Typeface.DEFAULT);

                }
            }
        });



    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();

            ImageView imageView = (ImageView) findViewById(R.id.imageToUpload);
            imageView.setImageURI(selectedImage);
            bm =((BitmapDrawable)imageView.getDrawable()).getBitmap();

        }
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }


}
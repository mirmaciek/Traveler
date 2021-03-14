package com.mirkiewicz.traveler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class PhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        int photo_id = intent.getIntExtra("photo_id",0);
        getIntent().removeExtra("photo_id");

        ImageView iv = findViewById(R.id.photo_imageView);
        iv.setImageBitmap(PhotosList.getfromList(photo_id));

        TextView tv = findViewById(R.id.note_textview_ap);
        tv.setText(MainActivity.database.getNote(photo_id).getNoteContent());
        Log.v("PhotoActivity", "Notatka: "+MainActivity.database.getNote(photo_id).getNoteContent() + "z indeksu: " + MainActivity.database.getNote(photo_id));


    }
}

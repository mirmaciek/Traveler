package com.mirkiewicz.traveler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CameraActivity extends AppCompatActivity {

    private Button canc_butt, save_butt;
    private EditText et_note;
    private int note_id;
    private String note_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);


        canc_butt = findViewById(R.id.cancel_butt);
        save_butt = findViewById(R.id.save_butt);
        et_note = findViewById(R.id.photo_editText);


        canc_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        save_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extras = getIntent().getExtras();
                note_id = PhotosList.getPhotos().size()-1;
                note_content = et_note.getText().toString().trim();
                Log.w("CameraActivity", "Proba stworzenia i dodania notatki do bazy"+note_id+note_content);
                Note note = new Note(note_id, note_content);
                MainActivity.database.addNote(note);
                Log.w("CameraActivity", "Pomyslnie dodano notatke do bazy"+note_id+note_content);
                finish();
            }
        });

    }

}

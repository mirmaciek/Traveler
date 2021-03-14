package com.mirkiewicz.traveler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class GalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        ListView listView = findViewById(R.id.photos_listview);
        MyArrayAdapter myArrayAdapter = new MyArrayAdapter(this, PhotosList.getPhotos());
        listView.setAdapter(myArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), PhotoActivity.class);
                intent.putExtra("photo_id",MainActivity.database.getNote(i).getIdNote());
                startActivity(intent);
            }
        });

    }
}

package com.mirkiewicz.traveler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "notification";
    ImageButton camera_btn, gallery_btn, settings_btn, about_btn;
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int LOCATION_REQUEST_CODE = 100;
    final Context context = this;
    public static NotesDatabase database;
    private static LocationListener locationListener;
    private static LocationManager locationManager;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions();



        database = new NotesDatabase(this);

        if((database.getAllNotes()).size() > 0  && PhotosList.getPhotos().size() == 0)
            database.removeAll();

        camera_btn = findViewById(R.id.camera_btn);
        gallery_btn = findViewById(R.id.gallery_btn);
        settings_btn = findViewById(R.id.settings_btn);
        about_btn = findViewById(R.id.about_btn);

        camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                //wyklad 7 albo 8

            }
        });

        gallery_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GalleryActivity.class);
                startActivity(intent);
            }
        });
        settings_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SettingsActivity.class);
                startActivity(intent);
            }
        });
        about_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "PRM Projekt 02 - Maciej Mirkiewicz s16426", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermissions();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {

            Location myloc = MyLocationListener.getLocation();

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(myloc.getLatitude(), myloc.getLongitude(), 1);
                if (addresses.size() > 0) {
                    System.out.println(addresses.get(0).getLocality());
                    System.out.println(addresses.get(0).getCountryName());
                    Log.wtf("TRY", addresses.get(0).getLocality());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
            String sdate = dateFormat.format(date);
            String city = addresses.get(0).getAddressLine(0);
            String string_photo = sdate + " " + city;

            Bitmap image_bmp = (Bitmap) data.getExtras().get("data");

            Config bitmapConfig = image_bmp.getConfig();
            image_bmp = image_bmp.copy(bitmapConfig, true);

            Canvas canvas = new Canvas(image_bmp);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Settings.getColor());
            paint.setTextSize(Settings.getSize());
            Rect bounds = new Rect();
            paint.getTextBounds(string_photo, 0, string_photo.length(), bounds);
            int x = 0;
            int y = image_bmp.getHeight() - 5;

            canvas.drawText(string_photo, x, y, paint);

            Intent intent = new Intent(context, CameraActivity.class);
            PhotosList.addtoList(image_bmp);
            Log.w("MainActivity", "Pomyslnie dodano zdjecie do listy");
            startActivity(intent);

        }
    }

    public void checkPermissions(){

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        }
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        }
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_REQUEST_CODE);
        }
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.ACCESS_BACKGROUND_LOCATION}, LOCATION_REQUEST_CODE);
        }
        else {
            locationListener = new MyLocationListener();
            locationManager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, locationListener);
        }


    }

}

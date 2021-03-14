package com.mirkiewicz.traveler;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner spinner;
    private EditText radius, textsize;
    private Button canc_butt, save_butt;
    private static final String[] colors = {"RED", "BLUE", "MAGENTA", "YELLOW", "GREEN", "WHITE", "BLACK"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final int previous_color = Settings.getColor();

        radius = findViewById(R.id.radius_edittext);
        textsize = findViewById(R.id.size_edittext);

        canc_butt = findViewById(R.id.cancel_butt);
        save_butt = findViewById(R.id.save_butt);

        canc_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.setColor(previous_color);
                finish();
            }
        });

        spinner = findViewById(R.id.color_spinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<>(SettingsActivity.this,
                android.R.layout.simple_spinner_item, colors);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        save_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sradius = radius.getText().toString().trim();
                String stextsize = textsize.getText().toString().trim();
                if(!(sradius.equals("") || stextsize.equals(""))){

                    int iRadius = Integer.parseInt(sradius.trim());
                    Settings.setRadius(iRadius);

                    int iTextsize = Integer.parseInt(stextsize.trim());
                    Settings.setSize(iTextsize);
                    Log.wtf("USTAWIENIA", "kolor: "+Settings.getColor()+", size: "+Settings.getSize()+"radius: "+Settings.getRadius());
                    finish();
                }else
                    Toast.makeText(getApplicationContext(), "Some fields are empty", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i){
            case 0:
                Settings.setColor(Color.RED);
                break;
            case 1:
                Settings.setColor(Color.BLUE);
                break;
            case 2:
                Settings.setColor(Color.MAGENTA);
                break;
            case 3:
                Settings.setColor(Color.YELLOW);
                break;
            case 4:
                Settings.setColor(Color.GREEN);
                break;
            case 5:
                Settings.setColor(Color.WHITE);
                break;
            case 6:
                Settings.setColor(Color.BLACK);
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

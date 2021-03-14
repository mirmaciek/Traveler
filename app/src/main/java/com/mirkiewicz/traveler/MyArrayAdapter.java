package com.mirkiewicz.traveler;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyArrayAdapter extends ArrayAdapter<Bitmap> {

    public MyArrayAdapter(@NonNull Context context, List<Bitmap> photos) {
        super(context, 0, photos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View v, @NonNull ViewGroup parent) {

        if (v == null) {

            v = LayoutInflater.from(getContext()).inflate(R.layout.photos_list_item, parent, false);
        }
        ImageView iv = v.findViewById(R.id.photo);
        iv.setImageBitmap(PhotosList.getfromList(position));

        return v;

    }
}

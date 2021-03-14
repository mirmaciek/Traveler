package com.mirkiewicz.traveler;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class PhotosList {

    private static List<Bitmap> photos = new ArrayList<>();

    public PhotosList() {
    }

    public static void addtoList(Bitmap photo){
        photos.add(photo);
    }
    public static Bitmap getfromList(int i){
        return photos.get(i);
    }

    public static List<Bitmap> getPhotos() {
        return photos;
    }
}

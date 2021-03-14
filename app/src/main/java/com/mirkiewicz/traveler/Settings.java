package com.mirkiewicz.traveler;

import android.graphics.Color;

public class Settings {

    private static int size = 12;
    private static int color = Color.RED;
    private static int radius = 1000; //domyslnie 1 km

    public static int getSize() {
        return size;
    }

    public static void setSize(int size) {
        Settings.size = size;
    }

    public static int getColor() {
        return color;
    }

    public static void setColor(int color) {
        Settings.color = color;
    }

    public static int getRadius() {
        return radius;
    }

    public static void setRadius(int radius) {
        Settings.radius = radius;
    }
}

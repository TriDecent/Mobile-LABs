package com.example.lab02_5.Models;

import com.example.lab02_5.R;

public enum DishesThumbnail {
    Tonkatsu("Tonkatsu", R.drawable.tonkatsu),
    Tempura("Tempura", R.drawable.tempura),
    Sukiyaki("Sukiyaki", R.drawable.sukiyaki),
    Udon("Udon", R.drawable.udon);

    final private String name;
    final private int image;

    DishesThumbnail(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getImg() {
        return image;
    }
}

package com.example.lab02_5;

import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.lab02_5.Models.DishesThumbnail;

public class ThumbnailManager {

    private final ArrayAdapter<DishesThumbnail> thumbnailAdapter;
    private final Spinner spFoodSelection;

    public ThumbnailManager(ArrayAdapter<DishesThumbnail> thumbnailAdapter, Spinner spFoodSelection) {
        this.thumbnailAdapter = thumbnailAdapter;
        this.spFoodSelection = spFoodSelection;
    }

    public void displayingThumbnails() {
        spFoodSelection.setAdapter(thumbnailAdapter);
        thumbnailAdapter.notifyDataSetChanged();
    }
}
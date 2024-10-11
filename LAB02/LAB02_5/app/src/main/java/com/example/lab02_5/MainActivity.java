package com.example.lab02_5;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private DishManager dishManager;
    private UIInitializer uiInitializer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uiInitializer = new UIInitializer(this);
        uiInitializer.initializeUI();

        dishManager = uiInitializer.getDishManager();
        ThumbnailManager thumbnailManager = uiInitializer.getThumbnailManager();

        thumbnailManager.displayingThumbnails();

        uiInitializer.getBtnAddNewDish().setOnClickListener(v -> {
            dishManager.addNewDish();
            resetUI();
        });

        uiInitializer.getGvDishes().setOnItemLongClickListener((parent, view, position, id) -> {
            dishManager.removeDish(position);
            return true;
        });
    }

    private void resetUI() {
        uiInitializer.resetUI();
    }
}

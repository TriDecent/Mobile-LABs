package com.example.lab02_5;

import android.widget.Button;
import android.widget.GridView;

public class EventHandler {
    private final Button btnAddNewDish;
    private final GridView gvDishes;
    private final DishManager dishManager;
    private final Runnable resetUICallback;

    public EventHandler(Button btnAddNewDish, GridView gvDishes, DishManager dishManager, Runnable resetUICallback) {
        this.btnAddNewDish = btnAddNewDish;
        this.gvDishes = gvDishes;
        this.dishManager = dishManager;
        this.resetUICallback = resetUICallback;
    }

    public void setupListeners() {
        btnAddNewDish.setOnClickListener(view -> {
            dishManager.addNewDish();
            resetUICallback.run();
        });

        gvDishes.setOnItemLongClickListener((parent, view, position, id) -> {
            dishManager.removeDish(position);
            return true;
        });
    }
}

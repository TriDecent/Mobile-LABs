package com.example.lab02_5;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;

public class ViewBinder {
    private final CheckBox cbIsPromoted;
    private final EditText etDishName;
    private final Spinner spFoodSelection;
    private final GridView gvDishes;
    private final Button btnAddNewDish;
    private final ThumbnailAdapter thumbnailAdapter;

    public ViewBinder(CheckBox cbIsPromoted, EditText etDishName, Spinner spFoodSelection,
                      GridView gvDishes, Button btnAddNewDish, ThumbnailAdapter thumbnailAdapter) {
        this.cbIsPromoted = cbIsPromoted;
        this.etDishName = etDishName;
        this.spFoodSelection = spFoodSelection;
        this.gvDishes = gvDishes;
        this.btnAddNewDish = btnAddNewDish;
        this.thumbnailAdapter = thumbnailAdapter;
    }

    public CheckBox getCbIsPromoted() {
        return cbIsPromoted;
    }

    public EditText getEtDishName() {
        return etDishName;
    }

    public Spinner getSpFoodSelection() {
        return spFoodSelection;
    }

    public GridView getGvDishes() {
        return gvDishes;
    }

    public Button getBtnAddNewDish() {
        return btnAddNewDish;
    }

    public ThumbnailAdapter getThumbnailAdapter() {
        return thumbnailAdapter;
    }

    public void resetFields() {
        etDishName.setText("");
        cbIsPromoted.setChecked(false);
        spFoodSelection.setSelection(0);
    }
}

package com.example.lab02_5;

import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;

import com.example.lab02_5.Models.Dish;
import com.example.lab02_5.Models.DishesThumbnail;

import java.util.ArrayList;

public class DishManager {

    private final ArrayList<Dish> dishes;
    private final ArrayAdapter<Dish> dishesAdapter;
    private final CheckBox cbPromoted;
    private final EditText etDishName;
    private final Spinner spFoodSelection;
    private final GridView gvDishes;

    public DishManager(
            ArrayList<Dish> dishes,
            ArrayAdapter<Dish> dishesAdapter,
            CheckBox cbPromoted,
            EditText etDishName,
            Spinner spFoodSelection,
            GridView gvDishes
    ) {
        this.dishes = dishes;
        this.dishesAdapter = dishesAdapter;
        this.cbPromoted = cbPromoted;
        this.etDishName = etDishName;
        this.spFoodSelection = spFoodSelection;
        this.gvDishes = gvDishes;
    }

    public void addNewDish() {
        String dishName = etDishName.getText().toString().trim();

        if (dishName.isEmpty()) {
            return;
        }

        int thumbnailIndex = spFoodSelection.getSelectedItemPosition();
        DishesThumbnail thumbnail = DishesThumbnail.values()[thumbnailIndex];

        Dish dish = new Dish(dishName, thumbnail, cbPromoted.isChecked());

        dishes.add(dish);

        // Notify the adapter that the data has changed
        gvDishes.setAdapter(dishesAdapter);
        dishesAdapter.notifyDataSetChanged();
    }

    public void removeDish(int position) {
        if (position >= 0 && position < dishes.size()) {
            dishes.remove(position);
            dishesAdapter.notifyDataSetChanged();
        }
    }
}

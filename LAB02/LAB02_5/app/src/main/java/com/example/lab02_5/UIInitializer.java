package com.example.lab02_5;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;

import com.example.lab02_5.Models.Dish;
import com.example.lab02_5.Models.DishesThumbnail;

import java.util.ArrayList;
import java.util.List;

public class UIInitializer {
    private final MainActivity activity;
    private DishManager dishManager;
    private ThumbnailManager thumbnailManager;
    private CheckBox cbIsPromoted;
    private EditText etDishName;
    private Spinner spFoodSelection;
    private GridView gvDishes;
    private Button btnAddNewDish;

    public UIInitializer(MainActivity activity) {
        this.activity = activity;
    }

    public void initializeUI() {
        ArrayList<Dish> dishes = new ArrayList<>();
        DishAdapter adapter = new DishAdapter(activity, R.layout.item_dish, dishes);
        spFoodSelection = activity.findViewById(R.id.sp_food_selection);
        ThumbnailAdapter thumbnailAdapter = new ThumbnailAdapter(activity, R.layout.items_thumbnails, List.of(DishesThumbnail.values()));
        cbIsPromoted = activity.findViewById(R.id.cb_is_promoted);
        etDishName = activity.findViewById(R.id.et_dish_name);
        gvDishes = activity.findViewById(R.id.gv_dishes);
        btnAddNewDish = activity.findViewById(R.id.btn_add_new_dish);

        thumbnailManager = new ThumbnailManager(thumbnailAdapter, spFoodSelection);
        dishManager = new DishManager(dishes, adapter, thumbnailAdapter, cbIsPromoted, etDishName, spFoodSelection, gvDishes);
    }

    public DishManager getDishManager() {
        return dishManager;
    }

    public ThumbnailManager getThumbnailManager() {
        return thumbnailManager;
    }

    public Button getBtnAddNewDish() {
        return btnAddNewDish;
    }

    public GridView getGvDishes() {
        return gvDishes;
    }

    public void resetUI() {
        etDishName.setText("");
        cbIsPromoted.setChecked(false);
        spFoodSelection.setSelection(0);
        thumbnailManager.displayingThumbnails();
    }
}
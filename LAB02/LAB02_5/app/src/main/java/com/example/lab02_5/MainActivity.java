package com.example.lab02_5;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab02_5.Models.Dish;
import com.example.lab02_5.Models.DishesThumbnail;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ThumbnailManager thumbnailManager;
    private ViewBinder viewBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        CheckBox cbIsPromoted = findViewById(R.id.cb_is_promoted);
        EditText etDishName = findViewById(R.id.et_dish_name);
        Spinner spFoodSelection = findViewById(R.id.sp_food_selection);
        GridView gvDishes = findViewById(R.id.gv_dishes);
        Button btnAddNewDish = findViewById(R.id.btn_add_new_dish);

        // Initialize ThumbnailAdapter
        ThumbnailAdapter thumbnailAdapter = new ThumbnailAdapter(this, R.layout.items_thumbnails, List.of(DishesThumbnail.values()));

        // Initialize ViewBinder
        viewBinder = new ViewBinder(cbIsPromoted, etDishName, spFoodSelection, gvDishes, btnAddNewDish, thumbnailAdapter);

        // Create the only list of dishes
        ArrayList<Dish> dishes = new ArrayList<>();

        // Initialize DishAdapter with the only list of dishes
        DishAdapter dishAdapter = new DishAdapter(this, R.layout.item_dish, dishes);

        // Initialize Managers with the only list of dishes
        DishManager dishManager = new DishManager(
                dishes,
                dishAdapter,
                viewBinder.getCbIsPromoted(),
                viewBinder.getEtDishName(),
                viewBinder.getSpFoodSelection(),
                viewBinder.getGvDishes()
        );

        thumbnailManager = new ThumbnailManager(viewBinder.getThumbnailAdapter(), viewBinder.getSpFoodSelection());
        thumbnailManager.displayThumbnails();

        // Initialize EventHandler
        EventHandler eventHandler = new EventHandler(
                viewBinder.getBtnAddNewDish(),
                viewBinder.getGvDishes(),
                dishManager,
                this::resetUI
        );
        eventHandler.setupListeners();
    }

    private void resetUI() {
        viewBinder.resetFields();
        thumbnailManager.displayThumbnails();
    }
}

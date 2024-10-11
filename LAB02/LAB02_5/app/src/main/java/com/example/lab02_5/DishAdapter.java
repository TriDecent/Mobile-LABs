package com.example.lab02_5;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.lab02_5.Models.Dish;

import java.util.List;

public class DishAdapter extends ArrayAdapter<Dish> {
    private final Activity context;

    public DishAdapter(Activity context, int layoutID, List<Dish> objects) {
        super(context, layoutID, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
//        If convertView is null, it means there is no recycled view available,
//        so a new view is created by inflating the item_employee layout.
//        The LayoutInflater is used to create a new view from the XML layout file.

//        convertView represents the view for a single item in the list
//        parent represents the container for the views

        convertView = (convertView == null) ?
                LayoutInflater
                        .from(context)
                        .inflate(R.layout.item_dish, parent, false) :
                convertView;

        // Get item
        Dish dish = getItem(position);

        // Get view
        ImageView ivThumbnail = convertView.findViewById(R.id.iv_dish_thumbnail);

        TextView tvDishName = convertView.findViewById(R.id.tv_dish_name);

        ImageView ivStar = convertView.findViewById(R.id.iv_star_image);

        // Set thumbnail of the dish
        ivThumbnail.setImageResource(dish.getThumbnail().getImg());

        // Set name of the dish
        tvDishName.setText(dish.getName() != null ? dish.getName() : "");

        // if the dish is promoted, show the star image, otherwise hide it
        ivStar.setVisibility(dish.isPromoted() ? View.VISIBLE : View.GONE);

        return convertView;
    }
}
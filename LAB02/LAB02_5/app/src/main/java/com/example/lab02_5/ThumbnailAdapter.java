package com.example.lab02_5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab02_5.Models.DishesThumbnail;

import java.util.List;

public class ThumbnailAdapter extends ArrayAdapter<DishesThumbnail> {

    public ThumbnailAdapter(Context context, int resource, List<DishesThumbnail> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = (convertView == null) ?
                LayoutInflater
                        .from(getContext())
                        .inflate(R.layout.items_thumbnails, parent, false) :
                convertView;

        TextView tvSelectedDish = convertView.findViewById(R.id.tv_dropdown_dish_name);
        ImageView ivSelectedThumbnail = convertView.findViewById(R.id.iv_dropdown_thumbnail);

        DishesThumbnail thumbnail = getItem(position);

        // Set the name and thumbnail of the selected dish
        tvSelectedDish.setText(thumbnail.getName());
        ivSelectedThumbnail.setImageResource(thumbnail.getImg());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = (convertView == null) ?
                LayoutInflater
                        .from(getContext())
                        .inflate(R.layout.items_thumbnails, parent, false) :
                convertView;

        TextView tvSelectedDish = convertView.findViewById(R.id.tv_dropdown_dish_name);
        ImageView ivSelectedThumbnail = convertView.findViewById(R.id.iv_dropdown_thumbnail);

        DishesThumbnail thumbnail = getItem(position);

        // Set the name and thumbnail of the selected dish
        tvSelectedDish.setText(thumbnail.getName());
        ivSelectedThumbnail.setImageResource(thumbnail.getImg());

        return convertView;
    }
}
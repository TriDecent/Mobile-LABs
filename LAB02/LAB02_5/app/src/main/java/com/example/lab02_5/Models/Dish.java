package com.example.lab02_5.Models;

public record Dish(String name, DishesThumbnail dishesThumbnail, boolean isPromoted) {
    public String getName() {
        return name;
    }

    public DishesThumbnail getThumbnail() {
        return dishesThumbnail;
    }

    public boolean isPromoted() {
        return isPromoted;
    }
}


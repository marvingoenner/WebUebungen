package com.example.console_http;

import java.util.List;
import com.example.console_http.Ingredient;


public class Recipe {
    private Long id;
    private String name;
    private String description;
    private double kcal;
    private double fett;
    private double kohlenhydrate;
    private double zucker;
    private double eiweiss;
    private double salz;
    private List<Ingredient> ingredients;

    // Getter-Methoden (damit Gson die Daten richtig einlesen kann)
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    // Optional: Falls du die kompletten Daten mal brauchst
    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", kcal=" + kcal +
                ", fett=" + fett +
                ", kohlenhydrate=" + kohlenhydrate +
                ", zucker=" + zucker +
                ", eiweiss=" + eiweiss +
                ", salz=" + salz +
                ", ingredients=" + ingredients +
                '}';
    }
}

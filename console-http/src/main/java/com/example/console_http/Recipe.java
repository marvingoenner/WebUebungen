package com.example.console_http;

import java.util.List;
import com.example.console_http.Ingredient;


public class Recipe {
    public Long id;
    public String name;
    public String description;
    public double kcal;
    public double fett;
    public double kohlenhydrate;
    public double zucker;
    public double eiweiss;
    public double salz;
    public List<Ingredient> ingredients;

    public double getKcal() {
        return kcal;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setKcal(double kcal) {
        this.kcal = kcal;
    }

    public void setFett(double fett) {
        this.fett = fett;
    }

    public void setKohlenhydrate(double kohlenhydrate) {
        this.kohlenhydrate = kohlenhydrate;
    }

    public void setZucker(double zucker) {
        this.zucker = zucker;
    }

    public void setEiweiss(double eiweiss) {
        this.eiweiss = eiweiss;
    }

    public void setSalz(double salz) {
        this.salz = salz;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public double getFett() {
        return fett;
    }

    public double getKohlenhydrate() {
        return kohlenhydrate;
    }

    public double getZucker() {
        return zucker;
    }

    public double getEiweiss() {
        return eiweiss;
    }

    public double getSalz() {
        return salz;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

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

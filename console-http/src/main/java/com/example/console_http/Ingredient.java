package com.example.console_http;

public class Ingredient {
    public Long id;
    public String name;
    public double kcal;
    public double fett;
    public double kohlenhydrate;
    public double zucker;
    public double eiweiss;
    public double salz;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getKcal() {
        return kcal;
    }

    public void setKcal(double kcal) {
        this.kcal = kcal;
    }

    public double getFett() {
        return fett;
    }

    public void setFett(double fett) {
        this.fett = fett;
    }

    public double getKohlenhydrate() {
        return kohlenhydrate;
    }

    public void setKohlenhydrate(double kohlenhydrate) {
        this.kohlenhydrate = kohlenhydrate;
    }

    public double getZucker() {
        return zucker;
    }

    public void setZucker(double zucker) {
        this.zucker = zucker;
    }

    public double getEiweiss() {
        return eiweiss;
    }

    public void setEiweiss(double eiweiss) {
        this.eiweiss = eiweiss;
    }

    public double getSalz() {
        return salz;
    }

    public void setSalz(double salz) {
        this.salz = salz;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
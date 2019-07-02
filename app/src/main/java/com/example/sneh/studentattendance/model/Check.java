package com.example.sneh.studentattendance.model;

public class Check {
    String name;
    String classes;
    String vehiclename;
    String date;
    String key;
    String vehicleno;
    String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getVehiclename() {
        return vehiclename;
    }

    public void setVehiclname(String vehiclename) {
        vehiclename = vehiclename;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }



    public Check(String name, String classes, String vehiclename, String date, String key, String image) {

        this.name = name;
        this.classes = classes;
        this.vehiclename = vehiclename;
        this.date = date;
        this.key = key;
        this.image=image;

    }

    public Check() {

    }
}

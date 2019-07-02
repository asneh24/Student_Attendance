package com.example.sneh.studentattendance.model;

public class Vehicle {
    String vichclename;
    String vichcleno;
    String phoneno;
    String name;
    String key;

    public String getVichclename()
    {
        return vichclename;
    }

    public void setVichclename(String vichclename)
    {
        this.vichclename = vichclename;
    }

    public String getVichcleno()
    {
        return vichcleno;
    }

    public void setVichcleno(String vichcleno)
    {
        this.vichcleno = vichcleno;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String key)
    {
        this.name = key;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Vehicle()
    {
    }

    public Vehicle(String vichclename, String vichcleno, String phoneno, String name,String key)
    {
        this.vichclename = vichclename;
        this.vichcleno = vichcleno;
        this.phoneno = phoneno;
        this.name = name;
        this.key = key;

    }
}

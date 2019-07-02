package com.example.sneh.studentattendance.model;

public class MenuModel
{
    String name;
    int image;

    public MenuModel(String name, int  image) {
        this.name = name;
        this.image = image;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int  getImage()
    {
        return image;
    }

    public void setImage(int  image)
    {
        this.image = image;
    }

    public MenuModel() {

    }


}

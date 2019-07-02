package com.example.sneh.studentattendance.model;

public class Studentinformation
{
    String image;
    String id;
    String name;
    String classe;
    String section;
    String address;
    String gender;
    String phoneno;
    String vehicletype;
    String key;


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }



    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public Studentinformation() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVehicletype() {
        return vehicletype;
    }

    public void setVehicletype(String vehicletype) {
        this.vehicletype = vehicletype;
    }

    public Studentinformation(String rollnum, String name, String classe, String section, String address, String gender, String phoneno, String vehicletype,String image, String key ) {
        this.id = rollnum;
        this.name = name;
        this.classe = classe;
        this.section = section;
        this.address = address;
        this.gender= gender;
        this.phoneno= phoneno;
        this.vehicletype = vehicletype;
        this.image=  image;
        this.key = key;
    }

}

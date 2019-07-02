package com.example.sneh.studentattendance.Activity;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class StudentAttendance extends Application
{
    Integer lang;
    String userid;
    SharedPreferences myPrefs;
    SharedPreferences.Editor prefEditor;
    Context context;

    ArrayList<String> arrayallstdname;

    public Integer getLang(Context context) {
        this.context = context;
        myPrefs = context.getSharedPreferences("appname", 0);
        return myPrefs.getInt("lang", 0);
    }

    public void setLang(Context context,Integer lang) {
        this.context = context;
        myPrefs = context.getSharedPreferences("appname", 0);
        prefEditor = myPrefs.edit();
        prefEditor.putInt("lang", lang);
        prefEditor.commit();
        this.lang = lang;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public ArrayList<String> getArrayallstdname() {
        return arrayallstdname;
    }

    public void setArrayallstdname(ArrayList<String> arrayallstdname) {
        this.arrayallstdname = arrayallstdname;
    }
}

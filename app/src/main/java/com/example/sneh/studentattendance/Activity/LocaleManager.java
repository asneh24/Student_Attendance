package com.example.sneh.studentattendance.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.Locale;

public class LocaleManager {

    public static final String SELECTED_LANGUAGE="Locale.Helper.Selected.Language";

    public static Context OnAttach(Context c)
    {
        String language=getLanguage(c,Locale.getDefault().getLanguage());
        return setNewLocale(c,language );
    }
    public static Context OnAttach(Context c,String defaultlanguage)
    {
        String language=getLanguage(c,Locale.getDefault().getLanguage());
        return setNewLocale(c,language );

    }
    public static Context setNewLocale(Context c, String language) {
        persistLanguage(c, language);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
            return updateResources(c, language);
        return updateResourcesLegncy(c,language);
    }

    private static Context updateResourcesLegncy(Context c, String language)
    {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = c.getResources();
        Configuration config = res.getConfiguration();
        config.locale = locale;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1)
            config.setLayoutDirection(locale);
        res.updateConfiguration(config,res.getDisplayMetrics());
        return c;
    }


    private static Context updateResources(Context c, String language)
    {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = c.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.locale = locale;
        config.setLayoutDirection(locale);
        return c.createConfigurationContext(config);
    }

    public static String getLanguage(Context c,String language)
    {
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(c);

        return preferences.getString(SELECTED_LANGUAGE,language);
    }

    private static void persistLanguage(Context c, String language)
    {
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(SELECTED_LANGUAGE,language);
        editor.apply();

    }


}

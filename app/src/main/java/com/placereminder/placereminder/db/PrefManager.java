package com.placereminder.placereminder.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.placereminder.placereminder.base.Application;

public class PrefManager {
    private static PrefManager ourInstance;
    private SharedPreferences mSharedPreferences;


    private PrefManager() {
        this.mSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(
                        Application.getInstance().getApplicationContext());
    }

    public static PrefManager getSharePref() {
        if (ourInstance == null) {
            ourInstance = new PrefManager();
        }
        return ourInstance;
    }

    public void saveABoolean(String key, boolean value) {
        mSharedPreferences.edit().putBoolean(key, value).apply();
    }

    public boolean getABoolean(String key, boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }
}

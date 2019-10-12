package com.placereminder.placereminder.base;

import androidx.multidex.MultiDexApplication;

public class Application extends MultiDexApplication {

    private static final String TAG = "App";
    private static Application instance;

    public static synchronized Application getInstance() {
        if (instance == null) {
            instance = new Application();
        }
        return instance;
    }

}

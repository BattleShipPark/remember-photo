package com.battleshippark.rememberphoto;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

/**
 */

public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }

    public static Context getContext() {
        return context;
    }
}

package com.battleshippark.rememberphoto;

import android.app.Application;
import android.content.Context;

import com.battleshippark.rememberphoto.db.DbOpenHelper;
import com.facebook.stetho.Stetho;

import java.sql.SQLException;

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

        try {
            DbOpenHelper.getInstance().createDao();
            DbOpenHelper.getInstance().getReadableDatabase().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Context getContext() {
        return context;
    }
}

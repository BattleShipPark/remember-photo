package com.battleshippark.rememberphoto.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.battleshippark.rememberphoto.App;
import com.battleshippark.rememberphoto.db.dto.Story;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 */

public class DbOpenHelper extends OrmLiteSqliteOpenHelper {
    private static final SQLiteOpenHelper INSTANCE = new DbOpenHelper(
            App.getContext(), "remember_photo", null, 1);

    private Dao<Story, Long> storyDao;

    private DbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Story.class);

            storyDao = getDao(Story.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    public static SQLiteOpenHelper getInstance() {
        return INSTANCE;
    }

    public Dao<Story, Long> getStoryDao() {
        return storyDao;
    }
}

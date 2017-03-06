package com.battleshippark.rememberphoto.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.battleshippark.rememberphoto.App;
import com.battleshippark.rememberphoto.db.dto.StoryDto;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 */

public class DbOpenHelper extends OrmLiteSqliteOpenHelper {
    private static final DbOpenHelper INSTANCE = new DbOpenHelper(
            App.getContext(), "remember_photo", null, 1);

    private Dao<StoryDto, Long> storyDao;

    private DbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, StoryDto.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    public static DbOpenHelper getInstance() {
        return INSTANCE;
    }

    public void createDao() throws SQLException {
        storyDao = getDao(StoryDto.class);
    }

    public Dao<StoryDto, Long> getStoryDao() throws SQLException {
        return storyDao;
    }
}

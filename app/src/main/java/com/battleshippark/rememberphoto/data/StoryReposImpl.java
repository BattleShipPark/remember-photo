package com.battleshippark.rememberphoto.data;

import com.battleshippark.rememberphoto.db.DbOpenHelper;
import com.battleshippark.rememberphoto.db.dto.StoryDto;

import java.sql.SQLException;
import java.util.List;

import rx.Observable;

/**
 */

public class StoryReposImpl implements StoryRepos {
    @Override
    public Observable<List<StoryDto>> queryList() throws SQLException {
        return Observable.just(DbOpenHelper.getInstance().getStoryDao().queryForAll());
    }

    @Override
    public Observable<StoryDto> query(long id) throws SQLException {
        return Observable.just(DbOpenHelper.getInstance().getStoryDao().queryForId(id));
    }
}

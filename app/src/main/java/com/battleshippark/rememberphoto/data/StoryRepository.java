package com.battleshippark.rememberphoto.data;

import com.battleshippark.rememberphoto.db.DbOpenHelper;
import com.battleshippark.rememberphoto.db.dto.StoryDto;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import rx.Observable;

/**
 */

public class StoryRepository implements StoryInteractor {
    @Override
    public Observable<List<StoryDto>> queryList() throws SQLException {
        QueryBuilder<StoryDto, Long> qb = DbOpenHelper.getInstance().getStoryDao().queryBuilder();
        List<StoryDto> list = qb.orderBy("id", false).query();
        return Observable.just(list);
    }

    @Override
    public Observable<StoryDto> query(long id) throws SQLException {
        return Observable.just(DbOpenHelper.getInstance().getStoryDao().queryForId(id));
    }

    @Override
    public Observable<Void> save(StoryDto storyDto) {
        return Observable.create(subscriber -> {
            try {
                DbOpenHelper.getInstance().getStoryDao().create(storyDto);
            } catch (SQLException e) {
                subscriber.onError(e);
            } finally {
                subscriber.onCompleted();
            }
        });
    }
}

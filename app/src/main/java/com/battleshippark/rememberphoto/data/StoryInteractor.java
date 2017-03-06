package com.battleshippark.rememberphoto.data;

import com.battleshippark.rememberphoto.db.dto.StoryDto;

import java.sql.SQLException;
import java.util.List;

import rx.Completable;
import rx.Observable;

/**
 */

public interface StoryInteractor {
    Observable<List<StoryDto>> queryList() throws SQLException;

    Observable<StoryDto> query(long id) throws SQLException;

    Observable<Void> save(StoryDto storyDto);
}

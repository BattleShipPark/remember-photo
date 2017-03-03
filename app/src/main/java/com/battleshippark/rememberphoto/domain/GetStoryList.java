package com.battleshippark.rememberphoto.domain;

import com.battleshippark.rememberphoto.data.StoryRepos;
import com.battleshippark.rememberphoto.db.dto.StoryDto;

import java.sql.SQLException;
import java.util.List;

import rx.Subscriber;

/**
 */

public class GetStoryList implements UseCase<Void, StoryList> {
    private StoryRepos storyRepos;
    private Mapper mapper;

    public GetStoryList(StoryRepos storyRepos, Mapper mapper) {
        this.storyRepos = storyRepos;
        this.mapper = mapper;
    }

    @Override
    public void execute(Void param, final Subscriber<StoryList> subscriber) {
        Subscriber<List<StoryDto>> innerSubscriber = new Subscriber<List<StoryDto>>() {
            @Override
            public void onCompleted() {
                subscriber.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                subscriber.onError(e);
            }

            @Override
            public void onNext(List<StoryDto> storyDtos) {
                subscriber.onNext(mapper.transformList(storyDtos));
            }
        };

        try {
            storyRepos.queryList().subscribe(innerSubscriber);
        } catch (SQLException e) {
            subscriber.onError(e);
        }
    }
}

package com.battleshippark.rememberphoto.domain;

import com.battleshippark.rememberphoto.data.StoryRepos;
import com.battleshippark.rememberphoto.db.dto.StoryDto;

import java.sql.SQLException;
import java.util.List;

import rx.Scheduler;
import rx.Subscriber;

/**
 */

public class GetStoryList implements UseCase<Void, StoryList> {
    private final StoryRepos storyRepos;
    private final DomainMapper domainMapper;
    private final Scheduler scheduler;
    private final Scheduler postScheduler;

    public GetStoryList(StoryRepos storyRepos, DomainMapper domainMapper, Scheduler scheduler, Scheduler postScheduler) {
        this.storyRepos = storyRepos;
        this.domainMapper = domainMapper;
        this.scheduler = scheduler;
        this.postScheduler = postScheduler;
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
                subscriber.onNext(domainMapper.transformList(storyDtos));
            }
        };

        try {
            storyRepos.queryList().subscribeOn(scheduler).observeOn(postScheduler).subscribe(innerSubscriber);
        } catch (SQLException e) {
            subscriber.onError(e);
        }
    }
}

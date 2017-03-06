package com.battleshippark.rememberphoto.domain;

import com.battleshippark.rememberphoto.data.StoryInteractor;
import com.battleshippark.rememberphoto.db.dto.StoryDto;

import java.sql.SQLException;
import java.util.List;

import rx.Scheduler;
import rx.Subscriber;

/**
 */

public class SaveStory implements UseCase<List<String>, Void> {
    private final StoryInteractor storyRepos;
    private final Scheduler scheduler;
    private final Scheduler postScheduler;

    public SaveStory(StoryInteractor storyRepos, Scheduler scheduler, Scheduler postScheduler) {
        this.storyRepos = storyRepos;
        this.scheduler = scheduler;
        this.postScheduler = postScheduler;
    }

    @Override
    public void execute(List<String> pathList, Subscriber<Void> subscriber) {
        Subscriber<Void> innerSubscriber = new Subscriber<Void>() {
            @Override
            public void onCompleted() {
                subscriber.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                subscriber.onError(e);
            }

            @Override
            public void onNext(Void aVoid) {
                subscriber.onNext(null);
            }
        };

        storyRepos.save(pathList).subscribeOn(scheduler).observeOn(postScheduler).subscribe(innerSubscriber);
    }
}

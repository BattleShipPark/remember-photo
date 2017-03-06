package com.battleshippark.rememberphoto.presentation.storydetail;

import com.battleshippark.rememberphoto.domain.DomainStory;
import com.battleshippark.rememberphoto.domain.UseCase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import rx.Subscriber;

/**
 */

class StoryDetailPresenter {
    private final long storyId;
    private final UiListener uiListener;
    private final UseCase<Long, DomainStory> getStory;
    private final PresentationMapper mapper;

    StoryDetailPresenter(long storyId, UiListener uiListener, UseCase<Long, DomainStory> getStory, PresentationMapper mapper) {
        this.storyId = storyId;
        this.uiListener = uiListener;
        this.getStory = getStory;
        this.mapper = mapper;
    }

    void load() {
        uiListener.showProgress();
        getStory.execute(storyId, new Subscriber<DomainStory>() {
            @Override
            public void onCompleted() {
                uiListener.hideProgress();
            }

            @Override
            public void onError(Throwable e) {
                uiListener.hideProgress();
                uiListener.showErrorPage();
            }

            @Override
            public void onNext(DomainStory storyList) {
                uiListener.update(mapper.transform(storyList));
            }
        });
    }

    Story createStory(List<String> pathList, long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        return new Story(dateFormat.format(cal.getTime()), pathList);
    }
}

package com.battleshippark.rememberphoto.presentation.storydetail;

import com.battleshippark.rememberphoto.domain.DomainStory;
import com.battleshippark.rememberphoto.domain.UseCase;

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
}

package com.battleshippark.rememberphoto.storylist.presentation;

import com.battleshippark.rememberphoto.domain.StoryList;
import com.battleshippark.rememberphoto.domain.UseCase;

import rx.Subscriber;

/**
 */

class StoryListPresenter {
    private UiListener uiListener;
    private UseCase<Void, StoryList> getStoryList;

    StoryListPresenter(UiListener uiListener, UseCase<Void, StoryList> getStoryList) {
        this.uiListener = uiListener;
        this.getStoryList = getStoryList;
    }

    void loadList() {
        uiListener.showProgress();
        getStoryList.execute(null, new Subscriber<StoryList>() {
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
            public void onNext(StoryList storyList) {
                uiListener.update(storyList);
            }
        });
    }
}

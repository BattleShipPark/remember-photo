package com.battleshippark.rememberphoto.presentation.storylist;

import com.battleshippark.rememberphoto.domain.DomainStoryList;
import com.battleshippark.rememberphoto.domain.UseCase;

import rx.Subscriber;

/**
 */

class StoryListPresenter {
    private final UiListener uiListener;
    private final UseCase<Void, DomainStoryList> getStoryList;
    private final PresentationMapper mapper;

    StoryListPresenter(UiListener uiListener, UseCase<Void, DomainStoryList> getStoryList, PresentationMapper mapper) {
        this.uiListener = uiListener;
        this.getStoryList = getStoryList;
        this.mapper = mapper;
    }

    void loadList() {
        uiListener.showProgress();
        getStoryList.execute(null, new Subscriber<DomainStoryList>() {
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
            public void onNext(DomainStoryList storyList) {
                StoryItemList storyItemList = mapper.transform(storyList);
                if (storyItemList.getItemList().isEmpty()) {
                    uiListener.showEmptyPage();
                } else {
                    uiListener.update(storyItemList);
                }
            }
        });
    }
}

package com.battleshippark.rememberphoto.presentation.storydetail;

import com.battleshippark.rememberphoto.db.dto.StoryDto;
import com.battleshippark.rememberphoto.domain.DomainStory;
import com.battleshippark.rememberphoto.domain.UseCase;

import java.util.Calendar;
import java.util.List;

import rx.Subscriber;

/**
 */

class StoryDetailPresenter {
    private final long storyId;
    private final UiListener uiListener;
    private final UseCase<Long, DomainStory> getStory;
    private UseCase<StoryDto, Void> saveStory;
    private final PresentationMapper mapper;
    private DomainStory domainStory;

    StoryDetailPresenter(long storyId, UiListener uiListener, UseCase<Long, DomainStory> getStory,
                         UseCase<StoryDto, Void> saveStory, PresentationMapper mapper) {
        this.storyId = storyId;
        this.uiListener = uiListener;
        this.getStory = getStory;
        this.saveStory = saveStory;
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

    Story createStory(long timestamp, List<String> pathList) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        this.domainStory = new DomainStory(cal.getTime(), pathList);
        return mapper.transform(domainStory);
    }

    void save(String title, String content) {
        StoryDto dto = new StoryDto(title, content, domainStory.getDate(), domainStory.getPhotoPathList().toArray(new String[]{}));
        saveStory.execute(dto, new Subscriber<Void>() {
            @Override
            public void onCompleted() {
                uiListener.saveDone();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                uiListener.hideProgress();
            }

            @Override
            public void onNext(Void aVoid) {
            }
        });
    }
}

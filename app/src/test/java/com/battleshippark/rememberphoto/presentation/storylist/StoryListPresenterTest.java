package com.battleshippark.rememberphoto.presentation.storylist;

import com.battleshippark.rememberphoto.domain.DomainStory;
import com.battleshippark.rememberphoto.domain.DomainStoryList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.verify;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class StoryListPresenterTest {
    @Mock
    UiListener uiListener;

    @Test
    public void loadList_empty() throws Exception {
        StoryListPresenter presenter = new StoryListPresenter(uiListener,
                (param, subscriber) -> {
                    subscriber.onNext(new DomainStoryList());
                    subscriber.onCompleted();
                }, new PresentationMapper());
        presenter.loadList();

        verify(uiListener).showEmptyPage();
    }

    @Test
    public void loadList() throws Exception {
        DomainStoryList domainStoryList = new DomainStoryList();
        domainStoryList.getGroupList().add(
                new DomainStoryList.Group(2017, 3, new ArrayList<>()));

        final PresentationMapper mapper = new PresentationMapper();
        StoryListPresenter presenter = new StoryListPresenter(uiListener,
                (param, subscriber) -> {
                    subscriber.onNext(domainStoryList);
                    subscriber.onCompleted();
                }, mapper);
        presenter.loadList();

        verify(uiListener).update(mapper.transform(domainStoryList));
        verify(uiListener).hideProgress();
    }
}
package com.battleshippark.rememberphoto.storylist.presentation;

import com.battleshippark.rememberphoto.domain.StoryList;

/**
 */

interface UiListener {
    void showProgress();

    void hideProgress();

    void showErrorPage();

    void update(StoryList storyList);
}

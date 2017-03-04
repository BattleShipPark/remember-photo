package com.battleshippark.rememberphoto.storylist.presentation;

/**
 */

interface UiListener {
    void showProgress();

    void hideProgress();

    void showErrorPage();

    void update(StoryList storyList);
}

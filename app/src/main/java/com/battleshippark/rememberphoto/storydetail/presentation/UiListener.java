package com.battleshippark.rememberphoto.storydetail.presentation;

/**
 */

interface UiListener {
    void showProgress();

    void hideProgress();

    void showErrorPage();

    void update(Story story);
}

package com.battleshippark.rememberphoto.presentation.storydetail;

/**
 */

interface UiListener {
    void showProgress();

    void hideProgress();

    void showErrorPage();

    void update(Story story);

    void saveDone();

    void setTopActionEnabled(boolean enabled);
}

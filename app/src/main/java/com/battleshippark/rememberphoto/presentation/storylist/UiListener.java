package com.battleshippark.rememberphoto.presentation.storylist;

/**
 */

interface UiListener {
    void showProgress();

    void hideProgress();

    void showErrorPage();

    void update(StoryItemList storyItemList);

    void onClickItem(StoryItemList.Item item);
}

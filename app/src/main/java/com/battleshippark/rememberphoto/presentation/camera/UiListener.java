package com.battleshippark.rememberphoto.presentation.camera;

/**
 */

interface UiListener {
    void showProgress();

    void hideProgress();

    void update(String path);
}

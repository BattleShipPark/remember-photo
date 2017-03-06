package com.battleshippark.rememberphoto.presentation.camera;

import com.battleshippark.rememberphoto.domain.UseCase;

import rx.Subscriber;

/**
 */

class CameraPresenter {
    private final UseCase<Void, String> takePicture;
    private final UiListener uiListener;

    CameraPresenter(UseCase<Void, String> takePicture, UiListener uiListener) {
        this.takePicture = takePicture;
        this.uiListener = uiListener;
    }

    void takePicture() {
        takePicture.execute(null, new Subscriber<String>() {
            @Override
            public void onCompleted() {
                uiListener.hideProgress();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                uiListener.hideProgress();
            }

            @Override
            public void onNext(String path) {
                uiListener.update(path);
            }
        });
    }
}

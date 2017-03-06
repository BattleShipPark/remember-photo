package com.battleshippark.rememberphoto.data;


import com.battleshippark.rememberphoto.camera.CameraController;

import rx.Observable;

/**
 */

public class CameraGadget implements CameraInteractor {
    private CameraController cameraController;

    public CameraGadget(CameraController cameraController) {
        this.cameraController = cameraController;
    }

    @Override
    public Observable<String> shot() {
        return Observable.create(subscriber -> {
            cameraController.takePicture(path -> {
                subscriber.onNext(path);
                subscriber.onCompleted();
            });
        });
    }
}

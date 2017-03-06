package com.battleshippark.rememberphoto.data;

import rx.Observable;

/**
 */

public interface CameraInteractor {
    Observable<String> shot();
}

package com.battleshippark.rememberphoto.domain;

import com.battleshippark.rememberphoto.data.CameraInteractor;

import rx.Scheduler;
import rx.Subscriber;

/**
 */

public class TakePicture implements UseCase<Void, String> {
    private final CameraInteractor cameraGadget;
    private final Scheduler scheduler;
    private final Scheduler postScheduler;

    public TakePicture(CameraInteractor cameraGadget, Scheduler scheduler, Scheduler postScheduler) {
        this.cameraGadget = cameraGadget;
        this.scheduler = scheduler;
        this.postScheduler = postScheduler;
    }


    @Override
    public void execute(Void param, Subscriber<String> subscriber) {
        cameraGadget.shot().subscribeOn(scheduler).observeOn(postScheduler).subscribe(subscriber);
    }
}

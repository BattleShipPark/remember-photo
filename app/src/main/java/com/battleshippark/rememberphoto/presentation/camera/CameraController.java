package com.battleshippark.rememberphoto.presentation.camera;

import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.annimon.stream.Stream;

import java.io.IOException;
import java.util.concurrent.Executors;

/**
 */

class CameraController {
    private Camera camera;

    void open() throws IOException {
        releaseCameraAndPreview();
        Executors.newCachedThreadPool().execute(() -> camera = Camera.open());
    }

    private void releaseCameraAndPreview() throws IOException {
        stopPreview();
        release();
    }

    void release() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    void startPreview(SurfaceHolder holder) throws IOException {
        camera.setPreviewDisplay(holder);
        camera.startPreview();

    }

    void stopPreview() throws IOException {
        if (camera != null) {
            camera.stopPreview();
            camera.setPreviewDisplay(null);
        }
    }

    void setParameters() {
        Camera.Parameters parameters = camera.getParameters();
        Camera.Size previewSize = Stream.of(parameters.getSupportedPreviewSizes()).filter(param -> 1f * param.width / param.height == 0.75f)
                .max((p1, p2) -> p1.width * p1.height - p2.width * p2.height).get();
        parameters.setPreviewSize(previewSize.width, previewSize.height);
        camera.setParameters(parameters);
    }
}

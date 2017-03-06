package com.battleshippark.rememberphoto.presentation.camera;

import android.hardware.Camera;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;

import com.annimon.stream.Stream;

import java.io.IOException;
import java.util.concurrent.Executors;

/**
 */

class CameraController {
    @Nullable
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
        if (camera != null) {
            camera.setDisplayOrientation(90);
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        }
    }

    void stopPreview() throws IOException {
        if (camera != null) {
            camera.stopPreview();
            camera.setPreviewDisplay(null);
        }
    }

    void setParameters() {
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            Camera.Size previewSize = Stream.of(parameters.getSupportedPreviewSizes()).filter(param -> 1f * param.height / param.width == 0.75f)
                    .max((p1, p2) -> p1.width * p1.height - p2.width * p2.height).get();
            parameters.setPreviewSize(previewSize.width, previewSize.height);
            camera.setParameters(parameters);
        }
    }
}

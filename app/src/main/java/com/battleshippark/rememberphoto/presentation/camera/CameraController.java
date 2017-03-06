package com.battleshippark.rememberphoto.presentation.camera;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.annimon.stream.Stream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 */

class CameraController {
    private static final String TAG = CameraController.class.getSimpleName();
    private final Activity activity;
    private final UiListener uiListener;
    private final ExecutorService executorService;
    @Nullable
    private Camera camera;

    CameraController(Activity activity, UiListener uiListener) {
        this.activity = activity;
        this.uiListener = uiListener;
        this.executorService = Executors.newCachedThreadPool();
    }

    void open() throws IOException {
        releaseCameraAndPreview();
        executorService.execute(() -> camera = Camera.open());
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
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            camera.setParameters(parameters);
        }
    }

    void takePicture() {
        if (camera != null) {
            camera.takePicture(null, null, (data, camera) -> {
                executorService.execute(() -> savePicture(data));
            });
        }
    }

    private void savePicture(byte[] data) {
        File pictureFile = getOutputImageFile();
        if (pictureFile == null) {
            Log.d(TAG, "Error creating media file, check storage permissions: ");
            return;
        }

        int angleToRotate = getRotationAngle(activity, Camera.CameraInfo.CAMERA_FACING_FRONT);
        Bitmap originalImage = BitmapFactory.decodeByteArray(data, 0, data.length);
        Bitmap bitmapImage = rotate(originalImage, angleToRotate);
        try {
            OutputStream fos = new FileOutputStream(pictureFile);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        } finally {
            uiListener.hideProgress();
        }
    }

    private int getRotationAngle(Activity mContext, int cameraId) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = mContext.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360; // compensate the mirror
        } else { // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        return result;
    }

    private Bitmap rotate(Bitmap bitmap, int degree) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix mtx = new Matrix();
        mtx.setRotate(degree);

        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }

    private File getOutputImageFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "RememberPhoto");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");
    }
}

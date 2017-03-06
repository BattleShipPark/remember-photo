package com.battleshippark.rememberphoto.presentation.camera;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.TextView;

import com.battleshippark.rememberphoto.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CameraActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    @BindView(R.id.surface_view)
    SurfaceView surfaceView;
    @BindView(R.id.cancel_text)
    TextView cancelText;
    @BindView(R.id.camera_image)
    ImageView cameraImage;
    @BindView(R.id.count_text)
    TextView countText;

    private SurfaceHolder surfaceHolder;
    private CameraController cameraController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);

        initData();
        initUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            cameraController.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        cameraController.release();
        super.onStop();
    }

    private void initData() {
       cameraController = new CameraController();
    }

    private void initUI() {
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            cameraController.startPreview(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (surfaceHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            cameraController.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here
        cameraController.setParameters();
        surfaceView.requestLayout();

        // start preview with new settings
        try {
            cameraController.startPreview(holder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            cameraController.stopPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

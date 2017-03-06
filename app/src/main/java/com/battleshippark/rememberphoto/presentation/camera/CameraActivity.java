package com.battleshippark.rememberphoto.presentation.camera;

import android.Manifest;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.battleshippark.rememberphoto.R;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraActivity extends AppCompatActivity implements SurfaceHolder.Callback, UiListener {
    @BindView(R.id.surface_view)
    SurfaceView surfaceView;
    @BindView(R.id.cancel_text)
    TextView cancelText;
    @BindView(R.id.camera_image)
    ImageView cameraImage;
    @BindView(R.id.count_text)
    TextView countText;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

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
    protected void onResume() {
        super.onResume();

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        try {
                            cameraController.open();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        new AlertDialog.Builder(CameraActivity.this)
                                .setMessage("You should grant CAMERA and WRITE_EXTERNAL_STORAGE").show();
                    }
                });
    }

    @Override
    protected void onPause() {
        cameraController.release();
        super.onPause();
    }

    private void initData() {
        cameraController = new CameraController(this, this);
    }

    private void initUI() {
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        ViewGroup.LayoutParams lp = surfaceView.getLayoutParams();
        lp.height = getResources().getDisplayMetrics().widthPixels / 3 * 4;
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

    @OnClick(R.id.camera_image)
    void onClickCamera() {
        showProgress();
        cameraController.takePicture();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        setButtonEnabled(false);
    }

    @Override
    public void hideProgress() {
        runOnUiThread(() -> {
            progressBar.setVisibility(View.GONE);
            setButtonEnabled(true);
        });
    }

    private void setButtonEnabled(boolean enabled) {
        cancelText.setEnabled(enabled);
        cameraImage.setEnabled(enabled);
        countText.setEnabled(enabled);
    }
}

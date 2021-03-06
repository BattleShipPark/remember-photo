package com.battleshippark.rememberphoto.presentation.camera;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.battleshippark.rememberphoto.R;
import com.battleshippark.rememberphoto.camera.CameraController;
import com.battleshippark.rememberphoto.data.CameraGadget;
import com.battleshippark.rememberphoto.domain.TakePicture;
import com.battleshippark.rememberphoto.presentation.storydetail.StoryDetailActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CameraActivity extends AppCompatActivity implements SurfaceHolder.Callback, UiListener {
    @BindView(R.id.surface_view)
    SurfaceView surfaceView;
    @BindView(R.id.cancel_text)
    TextView cancelText;
    @BindView(R.id.camera_image)
    ImageView cameraImage;
    @BindView(R.id.count_text)
    TextView countText;
    @BindView(R.id.save_text)
    TextView saveText;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private static final String KEY_PHOTO_LIST = "keyPhotoList";
    private SurfaceHolder surfaceHolder;
    private CameraController cameraController;
    private CameraPresenter presenter;
    private List<String> photoPathList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);

        initData(savedInstanceState);
        initUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            cameraController.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        cameraController.release();
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(KEY_PHOTO_LIST, (ArrayList<String>) photoPathList);
    }

    private void initData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            photoPathList = savedInstanceState.getStringArrayList(KEY_PHOTO_LIST);
        }

        cameraController = new CameraController(this);
        presenter = new CameraPresenter(
                new TakePicture(
                        new CameraGadget(cameraController), Schedulers.io(), AndroidSchedulers.mainThread()
                ), this
        );
    }

    private void initUI() {
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        ViewGroup.LayoutParams lp = surfaceView.getLayoutParams();
        lp.height = getResources().getDisplayMetrics().widthPixels / 3 * 4;

        countText.setText(getResources().getString(R.string.camera_photo_count, photoPathList.size()));
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

    @OnClick(R.id.cancel_text)
    void onClickCancel() {
        if (photoPathList.size() > 0) {
            new AlertDialog.Builder(this).setMessage(R.string.camera_cancel_alert)
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> finish())
                    .setNegativeButton(android.R.string.no, null).show();
        } else {
            finish();
        }
    }

    @OnClick(R.id.camera_image)
    void onClickCamera() {
        showProgress();
        presenter.takePicture();
    }

    @OnClick(R.id.save_text)
    void onClickSave() {
        if (photoPathList.size() > 0) {
            Intent intent = StoryDetailActivity.createIntent(this, photoPathList);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, R.string.camera_save_empty, Toast.LENGTH_SHORT).show();
        }
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

    @Override
    public void update(String path) {
        photoPathList.add(path);
        countText.setText(getResources().getString(R.string.camera_photo_count, photoPathList.size()));
    }

    private void setButtonEnabled(boolean enabled) {
        cancelText.setEnabled(enabled);
        cameraImage.setEnabled(enabled);
        cameraImage.setAlpha(enabled ? 1.0f : 0.25f);
        countText.setEnabled(enabled);
        saveText.setEnabled(enabled);
    }
}

package com.battleshippark.rememberphoto.presentation.storydetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.battleshippark.rememberphoto.R;
import com.battleshippark.rememberphoto.data.StoryRepository;
import com.battleshippark.rememberphoto.domain.DomainMapper;
import com.battleshippark.rememberphoto.domain.GetStory;
import com.battleshippark.rememberphoto.domain.SaveStory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class StoryDetailActivity extends AppCompatActivity implements UiListener {
    @BindView(R.id.top_title_text)
    protected TextView topTitleText;
    @BindView(R.id.top_done)
    protected TextView topDoneText;
    @BindView(R.id.count_text)
    protected TextView countText;
    @BindView(R.id.recycler_view)
    protected RecyclerView recyclerView;
    @BindView(R.id.title_text)
    protected EditText titleEdit;
    @BindView(R.id.content_text)
    protected EditText contentEdit;
    @BindView(R.id.date_text)
    protected TextView dateText;
    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;
    @BindView(R.id.error_layout)
    protected View errorLayout;

    private static final String KEY_MODE = "keyMode";
    private static final String KEY_STORY_ID = "keyStoryId";
    private static final String KEY_PATH_LIST = "keyPathList";
    private StoryDetailPhotoAdapter adapter;
    private StoryDetailPresenter presenter;

    private Mode mode;
    private long storyId;
    private List<String> pathList;
    private Story story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);
        ButterKnife.bind(this);

        initData(savedInstanceState);
        initUI();

        if (mode == Mode.VIEW || mode == Mode.EDIT) {
            presenter.load();
        } else {
            story = presenter.createStory(System.currentTimeMillis(), pathList);
            countText.setText(getResources().getQuantityString(R.plurals.story_detail_title_text, pathList.size(), pathList.size()));
            adapter.setItems(pathList);
            dateText.setText(story.getDate());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_MODE, mode.name());
        outState.putLong(KEY_STORY_ID, storyId);
        outState.putStringArrayList(KEY_PATH_LIST, (ArrayList<String>) pathList);
    }

    private void initData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mode = Mode.valueOf(getIntent().getStringExtra(KEY_MODE));
            storyId = getIntent().getLongExtra(KEY_STORY_ID, -1);
            pathList = getIntent().getStringArrayListExtra(KEY_PATH_LIST);
        } else {
            mode = Mode.valueOf(savedInstanceState.getString(KEY_MODE));
            storyId = savedInstanceState.getLong(KEY_STORY_ID);
            pathList = savedInstanceState.getStringArrayList(KEY_PATH_LIST);
        }
        adapter = new StoryDetailPhotoAdapter();

        final StoryRepository storyRepos = new StoryRepository();
        presenter = new StoryDetailPresenter(storyId, this,
                new GetStory(storyRepos, new DomainMapper(),
                        Schedulers.io(), AndroidSchedulers.mainThread()),
                new SaveStory(storyRepos, Schedulers.io(), AndroidSchedulers.mainThread()),
                new PresentationMapper());
    }

    private void initUI() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showErrorPage() {
        errorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void update(Story story) {
        countText.setText(
                getResources().getQuantityString(R.plurals.story_detail_title_text,
                        story.getPhotoPathList().size(), story.getPhotoPathList().size())
        );
        adapter.setItems(story.getPhotoPathList());
        titleEdit.setText(story.getTitle());
        contentEdit.setText(story.getContent());
        dateText.setText(getResources().getString(R.string.story_detail_date_text, story.getDate()));
    }

    @Override
    public void saveDone() {
        hideProgress();
        finish();
    }

    @OnClick(R.id.top_done)
    void onClickDone() {
        showProgress();
        presenter.save(titleEdit.getText().toString(), contentEdit.getText().toString());
    }

    public static Intent createIntent(Context context, long storyId) {
        Intent intent = new Intent(context, StoryDetailActivity.class);
        intent.putExtra(KEY_STORY_ID, storyId);
        return intent;
    }

    public static Intent createIntent(Context context, List<String> pathList) {
        Intent intent = new Intent(context, StoryDetailActivity.class);
        intent.putExtra(KEY_MODE, Mode.CREATE.name());
        intent.putStringArrayListExtra(KEY_PATH_LIST, (ArrayList<String>) pathList);
        return intent;
    }

    private enum Mode {
        VIEW, EDIT, CREATE
    }
}

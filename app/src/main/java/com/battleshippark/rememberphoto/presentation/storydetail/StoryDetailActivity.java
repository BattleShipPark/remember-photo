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

import com.battleshippark.rememberphoto.R;
import com.battleshippark.rememberphoto.data.StoryRepository;
import com.battleshippark.rememberphoto.domain.DomainMapper;
import com.battleshippark.rememberphoto.domain.GetStory;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class StoryDetailActivity extends AppCompatActivity implements UiListener {
    @BindView(R.id.tool_bar)
    protected Toolbar toolbar;
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

    private static final String KEY_STORY_ID = "keyStoryId";
    private StoryDetailPhotoAdapter adapter;
    private StoryDetailPresenter presenter;
    private long storyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);
        ButterKnife.bind(this);

        initData(savedInstanceState);
        initUI();

        presenter.load();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_STORY_ID, storyId);
    }

    private void initData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            storyId = getIntent().getLongExtra(KEY_STORY_ID, -1);
        } else {
            storyId = savedInstanceState.getLong(KEY_STORY_ID);
        }
        adapter = new StoryDetailPhotoAdapter();
        presenter = new StoryDetailPresenter(storyId, this,
                new GetStory(new StoryRepository(), new DomainMapper(),
                        Schedulers.io(), AndroidSchedulers.mainThread()), new PresentationMapper());
    }

    private void initUI() {
        setSupportActionBar(toolbar);

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

    public static Intent createIntent(Context context, long storyId) {
        Intent intent = new Intent(context, StoryDetailActivity.class);
        intent.putExtra(KEY_STORY_ID, storyId);
        return intent;
    }
}

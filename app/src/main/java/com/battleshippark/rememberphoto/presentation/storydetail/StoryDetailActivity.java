package com.battleshippark.rememberphoto.presentation.storydetail;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.battleshippark.rememberphoto.R;
import com.battleshippark.rememberphoto.data.StoryRepository;
import com.battleshippark.rememberphoto.domain.DomainMapper;
import com.battleshippark.rememberphoto.domain.GetStory;
import com.battleshippark.rememberphoto.domain.SaveStory;

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
    @BindView(R.id.top_action_text)
    protected TextView topActionText;
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
    private TextWatcher titleWatcher, contentWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);
        ButterKnife.bind(this);

        initData(savedInstanceState);
        initUI();
        updateUI();

        if (mode == Mode.VIEW || mode == Mode.EDIT) {
            presenter.load();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_MODE, mode.name());
        outState.putLong(KEY_STORY_ID, storyId);
        outState.putStringArrayList(KEY_PATH_LIST, (ArrayList<String>) pathList);
    }

    @Override
    public void onBackPressed() {
        switch (mode) {
            case VIEW:
                super.onBackPressed();
                break;
            case EDIT:
                if (topActionText.isEnabled()) {
                    showDialog();
                } else {
                    super.onBackPressed();
                }
                break;
            case CREATE:
                showDialog();
                break;
        }
    }

    private void showDialog() {
        new AlertDialog.Builder(this).setMessage(R.string.story_detail_exit_alert)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> super.onBackPressed())
                .setNegativeButton(android.R.string.no, null)
                .show();
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

        switch (mode) {
            case VIEW:
                break;
            case EDIT:
                break;
            case CREATE:
                story = presenter.createStory(System.currentTimeMillis(), pathList);
                break;
        }

        titleWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.onTitleChanged(s, story.getTitle());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        contentWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.onContentChanged(s, story.getContent());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }

    private void initUI() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }

    private void updateData() {
        switch (mode) {
            case VIEW:
                break;
            case EDIT:
                presenter.setEditMode(story);
                break;
            case CREATE:
                break;
        }
    }

    private void updateUI() {
        switch (mode) {
            case VIEW:
                topTitleText.setText(R.string.story_detail_top_title_detail);
                topActionText.setText(R.string.story_detail_top_action_text_edit);
                setEnabled(titleEdit, false);
                setEnabled(contentEdit, false);
                break;
            case EDIT:
                topTitleText.setText(R.string.story_detail_top_title_edit);
                topActionText.setText(R.string.story_detail_top_action_text_done);
                titleEdit.addTextChangedListener(titleWatcher);
                setEnabled(titleEdit, true);
                contentEdit.addTextChangedListener(contentWatcher);
                setEnabled(contentEdit, true);
                setTopActionEnabled(false);
                break;
            case CREATE:
                topTitleText.setText(R.string.story_detail_top_title_create);
                topActionText.setText(R.string.story_detail_top_action_text_done);
                topActionText.setEnabled(false);
                countText.setText(getResources().getQuantityString(R.plurals.story_detail_title_text, pathList.size(), pathList.size()));
                titleEdit.addTextChangedListener(titleWatcher);
                contentEdit.addTextChangedListener(contentWatcher);
                adapter.setItems(pathList);
                dateText.setText(story.getDate());
                break;
        }
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
        this.story = story;

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

    @Override
    public void setTopActionEnabled(boolean enabled) {
        if (mode != Mode.VIEW) {
            topActionText.setEnabled(enabled);
        }
    }

    @OnClick(R.id.top_action_text)
    void onClickTopActionText() {
        if (mode == Mode.VIEW) {
            mode = Mode.EDIT;
            updateData();
            updateUI();
        } else {
            showProgress();
            presenter.save(titleEdit.getText().toString(), contentEdit.getText().toString());
        }
    }

    private void setEnabled(EditText editText, boolean enabled) {
        editText.setFocusable(enabled);
        editText.setFocusableInTouchMode(enabled);
        editText.setClickable(enabled);
    }


    public static Intent createIntent(Context context, long storyId) {
        Intent intent = new Intent(context, StoryDetailActivity.class);
        intent.putExtra(KEY_MODE, Mode.VIEW.name());
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

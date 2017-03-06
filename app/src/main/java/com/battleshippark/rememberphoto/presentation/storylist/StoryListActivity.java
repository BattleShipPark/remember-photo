package com.battleshippark.rememberphoto.presentation.storylist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.battleshippark.rememberphoto.R;
import com.battleshippark.rememberphoto.data.StoryRepository;
import com.battleshippark.rememberphoto.domain.DomainMapper;
import com.battleshippark.rememberphoto.domain.GetStoryList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class StoryListActivity extends AppCompatActivity implements UiListener {
    @BindView(R.id.tool_bar)
    protected Toolbar toolbar;
    @BindView(R.id.recycler_view)
    protected RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;
    @BindView(R.id.error_layout)
    protected View errorLayout;

    private StoryListAdapter adapter;
    private StoryListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_list);
        ButterKnife.bind(this);

        initData(savedInstanceState);
        initUI();

        presenter.loadList();
    }

    private void initData(Bundle savedInstanceState) {
        adapter = new StoryListAdapter();
        presenter = new StoryListPresenter(this,
                new GetStoryList(new StoryRepository(), new DomainMapper(),
                        Schedulers.io(), AndroidSchedulers.mainThread()), new PresentationMapper());
    }

    private void initUI() {
        setSupportActionBar(toolbar);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.GONE);
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
    public void update(StoryItemList storyItemList) {
        adapter.setItems(storyItemList);
    }

    @OnClick(R.id.error_retry_btn)
    void retry() {
        errorLayout.setVisibility(View.GONE);
        presenter.loadList();
    }
}

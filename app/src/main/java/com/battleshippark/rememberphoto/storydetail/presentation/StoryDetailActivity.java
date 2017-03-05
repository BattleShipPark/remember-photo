package com.battleshippark.rememberphoto.storydetail.presentation;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.battleshippark.rememberphoto.R;
import com.battleshippark.rememberphoto.data.StoryRepository;
import com.battleshippark.rememberphoto.domain.DomainMapper;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class StoryDetailActivity extends AppCompatActivity implements UiListener {
    @BindView(R.id.tool_bar)
    protected Toolbar toolbar;
    @BindView(R.id.recycler_view)
    protected RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;
    @BindView(R.id.error_layout)
    protected View errorLayout;

    private StoryDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);
        ButterKnife.bind(this);

        initData(savedInstanceState);
        initUI();

        presenter.loadList();
    }

    private void initData(Bundle savedInstanceState) {
        adapter = new StoryDetailAdapter();
        presenter = new StoryDetailPresenter(this,
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
        adapter.setItem(story);
    }
}

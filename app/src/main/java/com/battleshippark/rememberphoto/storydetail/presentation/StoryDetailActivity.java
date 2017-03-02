package com.battleshippark.rememberphoto.storydetail.presentation;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.battleshippark.rememberphoto.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoryDetailActivity extends AppCompatActivity {
    @BindView(R.id.tool_bar)
    protected Toolbar toolbar;
    @BindView(R.id.view_pager)
    protected ViewPager viewPager;

    private StoryDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);
        ButterKnife.bind(this);

        initData(savedInstanceState);
        initUI();
    }

    private void initData(Bundle savedInstanceState) {
        adapter = new StoryDetailAdapter();
    }

    private void initUI() {
        setSupportActionBar(toolbar);

        viewPager.setAdapter(adapter);
    }
}

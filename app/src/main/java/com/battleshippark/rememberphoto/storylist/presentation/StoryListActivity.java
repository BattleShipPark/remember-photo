package com.battleshippark.rememberphoto.storylist.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.battleshippark.rememberphoto.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoryListActivity extends AppCompatActivity {
    @BindView(R.id.tool_bar)
    protected Toolbar toolbar;
    @BindView(R.id.recycler_view)
    protected RecyclerView recyclerView;

    private StoryListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_list);
        ButterKnife.bind(this);

        initData(savedInstanceState);
        initUI();
    }

    private void initData(Bundle savedInstanceState) {
        adapter = new StoryListAdapter();
    }

    private void initUI() {
        setSupportActionBar(toolbar);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);
    }
}

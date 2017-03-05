package com.battleshippark.rememberphoto.storylist.presentation;

import android.view.View;
import android.widget.TextView;

import com.battleshippark.rememberphoto.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */

class StoryHeaderVH extends StoryListVH {
    @BindView(R.id.date_text)
    TextView dateText;
    @BindView(R.id.count_text)
    TextView countText;

    StoryHeaderVH(View view) {
        super(view);
        ButterKnife.bind(view);
    }

    @Override
    void bind(int position, StoryItemList.Item item) {
        dateText.setText(item.date);
        countText.setText("(" + item.count + ")");
    }
}

package com.battleshippark.rememberphoto.storylist.presentation;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.battleshippark.rememberphoto.R;
import com.bumptech.glide.Glide;

import butterknife.BindView;

/**
 */

class StoryItemVH extends StoryListVH {
    @BindView(R.id.image_view)
    ImageView imageView;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.date_text)
    TextView dateText;

    StoryItemVH(View view) {
        super(view);
    }

    @Override
    void bind(int position, StoryItemList.Item item) {
        Glide.with(itemView.getContext()).load(item.story.getPhotoPath()).fitCenter()
                .into(imageView);
        titleText.setText(item.story.getTitle());
        dateText.setText(item.story.getDate());
    }
}

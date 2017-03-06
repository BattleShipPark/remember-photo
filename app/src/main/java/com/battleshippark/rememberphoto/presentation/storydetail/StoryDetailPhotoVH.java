package com.battleshippark.rememberphoto.presentation.storydetail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.battleshippark.rememberphoto.R;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */

class StoryDetailPhotoVH extends RecyclerView.ViewHolder {
    @BindView(R.id.image_view)
    ImageView imageView;

    StoryDetailPhotoVH(View itemView) {
        super(itemView);
        ButterKnife.bind(itemView);
    }

    void bind(String path) {
        Glide.with(itemView.getContext()).load(path).centerCrop().into(imageView);
    }
}

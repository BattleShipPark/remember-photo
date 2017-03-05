package com.battleshippark.rememberphoto.storydetail.presentation;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.battleshippark.rememberphoto.R;

/**
 */

class StoryDetailAdapter extends RecyclerView.Adapter<StoryDetailPhotoVH> {
    private Story item;

    @Override
    public StoryDetailPhotoVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StoryDetailPhotoVH(
                View.inflate(parent.getContext(), R.layout.listitem_story_detail_photo, null));
    }

    @Override
    public void onBindViewHolder(StoryDetailPhotoVH holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setItem(Story item) {
        this.item = item;
    }
}

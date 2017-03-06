package com.battleshippark.rememberphoto.presentation.storydetail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.battleshippark.rememberphoto.R;

import java.util.List;

/**
 */

class StoryDetailPhotoAdapter extends RecyclerView.Adapter<StoryDetailPhotoVH> {
    private List<String> photoPathList;

    @Override
    public StoryDetailPhotoVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StoryDetailPhotoVH(
                View.inflate(parent.getContext(), R.layout.listitem_story_detail_photo, null));
    }

    @Override
    public void onBindViewHolder(StoryDetailPhotoVH holder, int position) {
        holder.bind(photoPathList.get(position));
    }

    @Override
    public int getItemCount() {
        return photoPathList == null ? 0 : photoPathList.size();
    }

    void setItems(List<String> photoPathList) {
        this.photoPathList = photoPathList;
        notifyDataSetChanged();
    }
}

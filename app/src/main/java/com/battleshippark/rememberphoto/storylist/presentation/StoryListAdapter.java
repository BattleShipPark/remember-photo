package com.battleshippark.rememberphoto.storylist.presentation;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 */

class StoryListAdapter extends RecyclerView.Adapter<StoryListVH> {
    private StoryItemList storyItemList;

    @Override
    public StoryListVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(StoryListVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return storyItemList == null ? 0 : storyItemList.getItemList().size();
    }

    void setItems(StoryItemList storyItemList) {
        this.storyItemList = storyItemList;
        notifyDataSetChanged();
    }
}

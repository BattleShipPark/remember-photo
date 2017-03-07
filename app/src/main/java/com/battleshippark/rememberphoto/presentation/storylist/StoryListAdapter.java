package com.battleshippark.rememberphoto.presentation.storylist;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 */

class StoryListAdapter extends RecyclerView.Adapter<StoryListVH> {
    private StoryItemList storyItemList;
    private UiListener uiListener;

    StoryListAdapter(UiListener uiListener) {
        this.uiListener = uiListener;
    }

    @Override
    public StoryListVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return StoryListVH.create(parent.getContext(), parent, viewType);
    }

    @Override
    public void onBindViewHolder(StoryListVH holder, int position) {
        holder.bind(position, storyItemList.getItemList().get(position), uiListener);
    }

    @Override
    public int getItemViewType(int position) {
        return storyItemList.getItemList().get(position).type.ordinal();
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

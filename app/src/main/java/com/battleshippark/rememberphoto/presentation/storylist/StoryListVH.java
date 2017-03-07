package com.battleshippark.rememberphoto.presentation.storylist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.battleshippark.rememberphoto.R;

/**
 */

abstract class StoryListVH extends RecyclerView.ViewHolder {
    StoryListVH(View itemView) {
        super(itemView);
    }

    static StoryListVH create(Context context, ViewGroup parent, int viewType) {
        StoryItemList.Type type = StoryItemList.Type.values()[viewType];
        switch (type) {
            case HEADER:
                return new StoryHeaderVH(
                        LayoutInflater.from(context).inflate(R.layout.listitem_story_list_group_header, parent, false)
                );
            case STORY:
                return new StoryItemVH(
                        LayoutInflater.from(context).inflate(R.layout.listitem_story_list_group_item, parent, false)
                );
            default:
                throw new IllegalArgumentException();
        }
    }

    abstract void bind(int position, StoryItemList.Item item);
}

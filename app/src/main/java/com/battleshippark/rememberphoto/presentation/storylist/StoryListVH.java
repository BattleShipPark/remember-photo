package com.battleshippark.rememberphoto.presentation.storylist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.battleshippark.rememberphoto.R;

/**
 */

abstract class StoryListVH extends RecyclerView.ViewHolder {
    StoryListVH(View itemView) {
        super(itemView);
    }

    static StoryListVH create(Context context, int viewType) {
        StoryItemList.Type type = StoryItemList.Type.values()[viewType];
        switch (type) {
            case HEADER:
                return new StoryHeaderVH(
                        View.inflate(context, R.layout.listitem_story_list_group_header, null)
                );
            case STORY:
                return new StoryItemVH(
                        View.inflate(context, R.layout.listitem_story_list_group_header, null)
                );
            default:
                throw new IllegalArgumentException();
        }
    }

    abstract void bind(int position, StoryItemList.Item item);
}

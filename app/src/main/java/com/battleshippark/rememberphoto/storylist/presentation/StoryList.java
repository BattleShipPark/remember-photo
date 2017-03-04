package com.battleshippark.rememberphoto.storylist.presentation;

import com.annimon.stream.Stream;
import com.battleshippark.rememberphoto.domain.DomainStoryList;
import com.battleshippark.rememberphoto.domain.Story;

import java.util.ArrayList;
import java.util.List;

/**
 */

class StoryList {
    private final List<Item> itemList = new ArrayList<>();

    StoryList(DomainStoryList domainStoryList) {
        Stream.of(domainStoryList.getGroupList()).forEach(group -> {
            itemList.add(new Item(group.getYear(), group.getMonth()));
            group.getStoryList().forEach(story -> itemList.add(new Item(story)));
        });
    }

    List<Item> getItemList() {
        return itemList;
    }

    enum Type {
        HEADER, STORY
    }

    static class Item {
        final Type type;
        final int year;
        final int month;
        final Story story;

        Item(int year, int month) {
            this.type = Type.HEADER;
            this.year = year;
            this.month = month;
            this.story = null;
        }

        Item(Story story) {
            this.type = Type.STORY;
            this.year = -1;
            this.month = -1;
            this.story = story;
        }
    }
}

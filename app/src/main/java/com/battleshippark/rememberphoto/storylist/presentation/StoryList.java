package com.battleshippark.rememberphoto.storylist.presentation;

import android.support.annotation.VisibleForTesting;

import com.annimon.stream.Stream;
import com.battleshippark.rememberphoto.domain.DomainStoryList;

import java.util.ArrayList;
import java.util.List;

/**
 */

class StoryList {
    private final List<Item> itemList = new ArrayList<>();

    StoryList(DomainStoryList domainStoryList) {
        Stream.of(domainStoryList.getGroupList()).forEach(group -> {
            itemList.add(new Item(group.getYear(), group.getMonth(), group.getStoryList().size()));
            group.getStoryList().forEach(domainStory -> itemList.add(new Item(new Story(domainStory))));
        });
    }

    @VisibleForTesting
    StoryList(List<Item> itemList) {
        this.itemList.addAll(itemList);
    }

    List<Item> getItemList() {
        return itemList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StoryList storyList = (StoryList) o;

        return itemList.equals(storyList.itemList);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "StoryList{" +
                "itemList=" + itemList +
                '}';
    }

    enum Type {
        HEADER, STORY
    }

    static class Item {
        final Type type;
        final int year;
        final int month;
        final int count;
        final Story story;

        Item(int year, int month, int count) {
            this.type = Type.HEADER;
            this.year = year;
            this.month = month;
            this.count = count;
            this.story = null;
        }

        Item(Story story) {
            this.type = Type.STORY;
            this.year = -1;
            this.month = -1;
            this.count = -1;
            this.story = story;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Item item = (Item) o;

            if (year != item.year) return false;
            if (month != item.month) return false;
            if (count != item.count) return false;
            if (type != item.type) return false;
            return story != null ? story.equals(item.story) : item.story == null;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "type=" + type +
                    ", year=" + year +
                    ", month=" + month +
                    ", count=" + count +
                    ", story=" + story +
                    '}';
        }
    }
}

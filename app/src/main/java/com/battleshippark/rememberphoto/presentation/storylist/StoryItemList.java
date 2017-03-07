package com.battleshippark.rememberphoto.presentation.storylist;

import android.support.annotation.VisibleForTesting;

import com.annimon.stream.Stream;
import com.battleshippark.rememberphoto.domain.DomainStoryList;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 */

class StoryItemList {
    private final List<Item> itemList = new ArrayList<>();

    StoryItemList(DomainStoryList domainStoryList) {
        Stream.of(domainStoryList.getGroupList()).forEach(group -> {
            itemList.add(new Item(group.getYear(), group.getMonth(), group.getStoryList().size()));
            Stream.of(group.getStoryList()).forEach(domainStory -> itemList.add(new Item(new Story(domainStory))));
        });
    }

    @VisibleForTesting
    StoryItemList(List<Item> itemList) {
        this.itemList.addAll(itemList);
    }

    List<Item> getItemList() {
        return itemList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StoryItemList storyItemList = (StoryItemList) o;

        return itemList.equals(storyItemList.itemList);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "StoryItemList{" +
                "itemList=" + itemList +
                '}';
    }

    enum Type {
        HEADER, STORY
    }

    static class Item {
        final Type type;
        final String date;
        final int count;
        final Story story;

        Item(int year, int month, int count) {
            this.type = Type.HEADER;
            this.date = String.format(Locale.US, "%d / %02d", year, month);
            this.count = count;
            this.story = null;
        }

        Item(Story story) {
            this.type = Type.STORY;
            this.date = null;
            this.count = -1;
            this.story = story;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Item item = (Item) o;

            if (count != item.count) return false;
            if (type != item.type) return false;
            if (date != null ? !date.equals(item.date) : item.date != null) return false;
            return story != null ? story.equals(item.story) : item.story == null;

        }

        @Override
        public int hashCode() {
            int result = type != null ? type.hashCode() : 0;
            result = 31 * result + (date != null ? date.hashCode() : 0);
            result = 31 * result + count;
            result = 31 * result + (story != null ? story.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "type=" + type +
                    ", date='" + date + '\'' +
                    ", count=" + count +
                    ", story=" + story +
                    '}';
        }
    }
}

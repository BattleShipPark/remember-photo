package com.battleshippark.rememberphoto.domain;

import java.util.ArrayList;
import java.util.List;

/**
 */

public class StoryList {
    private final List<Story> storyList;

    public StoryList() {
        storyList = new ArrayList<>();
    }

    public void add(Story story) {
        storyList.add(story);
    }
}

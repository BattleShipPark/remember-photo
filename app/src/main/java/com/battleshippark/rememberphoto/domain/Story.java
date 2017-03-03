package com.battleshippark.rememberphoto.domain;

import com.battleshippark.rememberphoto.db.dto.StoryDto;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 */

public class Story {
    private final long id;
    private final String title;
    private final String content;
    private final Date date;
    private final List<String> photoPathList;

    public Story(StoryDto storyDto) {
        id = storyDto.getId();
        title = storyDto.getTitle();
        content = storyDto.getContent();
        date = storyDto.getDate();
        photoPathList = Arrays.asList(storyDto.getPhotoPathList());
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public List<String> getPhotoPathList() {
        return photoPathList;
    }

}

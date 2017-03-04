package com.battleshippark.rememberphoto.domain;

import android.support.annotation.VisibleForTesting;

import com.battleshippark.rememberphoto.db.dto.StoryDto;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 */

public class DomainStory {
    private final long id;
    private final String title;
    private final String content;
    private final Date date;
    private final List<String> photoPathList;

    public DomainStory(StoryDto storyDto) {
        id = storyDto.getId();
        title = storyDto.getTitle();
        content = storyDto.getContent();
        date = storyDto.getDate();
        photoPathList = Arrays.asList(storyDto.getPhotoPathList());
    }

    @VisibleForTesting
    public DomainStory(long id, String title, String content, Date date, List<String> photoPathList) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.photoPathList = photoPathList;
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

package com.battleshippark.rememberphoto.storylist.presentation;

import android.support.annotation.VisibleForTesting;

import com.battleshippark.rememberphoto.db.dto.StoryDto;
import com.battleshippark.rememberphoto.domain.DomainStory;

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

    public Story(DomainStory domainStory) {
        id = domainStory.getId();
        title = domainStory.getTitle();
        content = domainStory.getContent();
        date = domainStory.getDate();
        photoPathList = domainStory.getPhotoPathList();
    }

    @VisibleForTesting
    public Story(long id, String title, String content, Date date, List<String> photoPathList) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Story story = (Story) o;

        if (id != story.id) return false;
        if (title != null ? !title.equals(story.title) : story.title != null) return false;
        if (content != null ? !content.equals(story.content) : story.content != null) return false;
        if (date != null ? !date.equals(story.date) : story.date != null) return false;
        return photoPathList != null ? photoPathList.equals(story.photoPathList) : story.photoPathList == null;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "Story{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", photoPathList=" + photoPathList +
                '}';
    }
}

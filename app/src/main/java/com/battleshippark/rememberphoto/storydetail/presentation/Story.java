package com.battleshippark.rememberphoto.storydetail.presentation;

import android.support.annotation.VisibleForTesting;

import com.battleshippark.rememberphoto.domain.DomainStory;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 */

class Story {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    private final long id;
    private final String title;
    private final String content;
    private final String date;
    private final List<String> photoPathList;

    Story(DomainStory domainStory) {
        id = domainStory.getId();
        title = domainStory.getTitle();
        content = domainStory.getContent();
        date = dateFormat.format(domainStory.getDate());
        photoPathList = domainStory.getPhotoPathList();
    }

    @VisibleForTesting
    Story(long id, String title, String content, String date, List<String> photoPathList) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.photoPathList = photoPathList;
    }

    long getId() {
        return id;
    }

    String getTitle() {
        return title;
    }

    String getContent() {
        return content;
    }

    String getDate() {
        return date;
    }

    List<String> getPhotoPathList() {
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

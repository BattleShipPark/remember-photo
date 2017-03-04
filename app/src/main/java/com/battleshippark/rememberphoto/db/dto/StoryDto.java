package com.battleshippark.rememberphoto.db.dto;

import android.support.annotation.VisibleForTesting;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 */

@DatabaseTable
public class StoryDto {
    @DatabaseField(id = true)
    private long id;
    @DatabaseField
    private String title;
    @DatabaseField
    private String content;
    @DatabaseField(index = true, dataType = DataType.DATE_LONG)
    private Date date;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private String[] photoPathList;

    public StoryDto() {
    }

    @VisibleForTesting
    public StoryDto(String title, String content, Date date, String[] photoPathList) {
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

    public String[] getPhotoPathList() {
        return photoPathList;
    }
}

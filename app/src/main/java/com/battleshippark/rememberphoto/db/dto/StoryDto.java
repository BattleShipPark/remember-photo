package com.battleshippark.rememberphoto.db.dto;

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

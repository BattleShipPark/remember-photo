package com.battleshippark.rememberphoto.presentation.storydetail;


import com.battleshippark.rememberphoto.domain.DomainStory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 */

class PresentationMapper {
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

    Story transform(DomainStory domainStory) {
        return new Story(domainStory.getId(), domainStory.getTitle(), domainStory.getContent(),
                dateFormat.format(domainStory.getDate()), domainStory.getPhotoPathList());
    }

    DomainStory transform(Story story) throws ParseException {
        return new DomainStory(story.getId(), story.getTitle(), story.getContent(),
                dateFormat.parse(story.getDate()), story.getPhotoPathList());
    }
}

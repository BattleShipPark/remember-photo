package com.battleshippark.rememberphoto.domain;

import com.annimon.stream.Stream;
import com.battleshippark.rememberphoto.db.dto.StoryDto;

import java.util.List;

/**
 */

public class DomainMapper {
    StoryList transformList(List<StoryDto> storyDtoList) {
        List<Story> list = Stream.of(storyDtoList).map(this::transformItem).toList();
        return new StoryList(list);
    }

    Story transformItem(StoryDto storyDto) {
        return new Story(storyDto);
    }
}

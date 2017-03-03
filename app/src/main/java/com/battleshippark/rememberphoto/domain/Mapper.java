package com.battleshippark.rememberphoto.domain;

import com.annimon.stream.Stream;
import com.battleshippark.rememberphoto.db.dto.StoryDto;

import java.util.List;

/**
 */

class Mapper {
    StoryList transformList(List<StoryDto> storyDtoList) {
        StoryList storyList = new StoryList();
        Stream.of(storyDtoList).map(this::transformItem).forEach(storyList::add);
        return storyList;
    }

    Story transformItem(StoryDto storyDto) {
        return new Story(storyDto);
    }
}

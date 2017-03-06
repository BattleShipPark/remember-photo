package com.battleshippark.rememberphoto.presentation.storylist;


import com.battleshippark.rememberphoto.domain.DomainStoryList;

/**
 */

class PresentationMapper {
    StoryItemList transform(DomainStoryList domainStoryList) {
        return new StoryItemList(domainStoryList);
    }
}

package com.battleshippark.rememberphoto.storylist.presentation;


import com.battleshippark.rememberphoto.domain.DomainStoryList;

/**
 */

class PresentationMapper {
    StoryItemList transform(DomainStoryList domainStoryList) {
        return new StoryItemList(domainStoryList);
    }
}

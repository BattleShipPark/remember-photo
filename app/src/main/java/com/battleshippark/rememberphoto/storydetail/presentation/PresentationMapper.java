package com.battleshippark.rememberphoto.storydetail.presentation;


import com.battleshippark.rememberphoto.domain.DomainStory;

/**
 */

class PresentationMapper {
    Story transform(DomainStory domainStory) {
        return new Story(domainStory);
    }
}

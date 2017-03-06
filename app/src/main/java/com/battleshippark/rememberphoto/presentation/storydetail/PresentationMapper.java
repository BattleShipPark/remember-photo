package com.battleshippark.rememberphoto.presentation.storydetail;


import com.battleshippark.rememberphoto.domain.DomainStory;

/**
 */

class PresentationMapper {
    Story transform(DomainStory domainStory) {
        return new Story(domainStory);
    }
}

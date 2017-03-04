package com.battleshippark.rememberphoto.storylist.presentation;

import com.battleshippark.rememberphoto.domain.DomainStoryList;
import com.battleshippark.rememberphoto.domain.Story;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Java6Assertions.assertThat;


/**
 */
public class PresentationMapperTest {
    @Test
    public void transform_empty() throws Exception {
        PresentationMapper mapper = new PresentationMapper();
        DomainStoryList domainStoryList = new DomainStoryList(Collections.EMPTY_LIST);

        StoryList storyList = mapper.transform(domainStoryList);

        assertThat(storyList.getItemList()).isEmpty();
    }

    @Test
    public void transform() throws Exception {
        PresentationMapper mapper = new PresentationMapper();
        DomainStoryList domainStoryList = new DomainStoryList(
                Arrays.asList(new Story());
        );

        StoryList storyList = mapper.transform(domainStoryList);

        assertThat(storyList.getItemList()).isEmpty();
    }
}
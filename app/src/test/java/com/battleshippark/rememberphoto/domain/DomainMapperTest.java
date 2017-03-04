package com.battleshippark.rememberphoto.domain;

import com.battleshippark.rememberphoto.db.dto.StoryDto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class DomainMapperTest {
    @Test
    public void transformList_empty() throws Exception {
        List<StoryDto> storyDtoList = Collections.emptyList();
        DomainStoryList expectedStoryList = new DomainStoryList();
        DomainMapper mapper = new DomainMapper();

        DomainStoryList storyList = mapper.transformList(storyDtoList);

        assertThat(storyList).isEqualTo(expectedStoryList);
    }

    @Test
    public void transformList() throws Exception {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        List<StoryDto> storyDtoList = Arrays.asList(
                new StoryDto("title1", "content1", dateFormat.parse("2017-03-01 09:00:00"),
                        new String[]{"image1", "image2"}),
                new StoryDto("title2", "content2", dateFormat.parse("2017-03-01 08:50:10"),
                        new String[]{"image3", "image4"}),
                new StoryDto("title3", "content3", dateFormat.parse("2017-02-28 01:40:30"),
                        new String[]{"image5"})
        );
        DomainMapper mapper = new DomainMapper();

        DomainStoryList storyList = mapper.transformList(storyDtoList);


        assertThat(storyList.getGroupList()).hasSize(2);

        DomainStoryList.Group group = storyList.getGroupList().get(0);
        assertThat(group.getYear()).isEqualTo(2017);
        assertThat(group.getMonth()).isEqualTo(3);
        assertThat(group.getStoryList()).hasSize(2);

        group = storyList.getGroupList().get(1);
        assertThat(group.getYear()).isEqualTo(2017);
        assertThat(group.getMonth()).isEqualTo(2);
        assertThat(group.getStoryList()).hasSize(1);
    }
}
package com.battleshippark.rememberphoto.presentation.storylist;

import com.battleshippark.rememberphoto.domain.DomainStoryList;
import com.battleshippark.rememberphoto.domain.DomainStory;
import com.battleshippark.rememberphoto.presentation.storylist.PresentationMapper;
import com.battleshippark.rememberphoto.presentation.storylist.Story;
import com.battleshippark.rememberphoto.presentation.storylist.StoryItemList;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;

import static org.assertj.core.api.Java6Assertions.assertThat;


/**
 */
public class PresentationMapperTest {
    private final PresentationMapper mapper = new PresentationMapper();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

    @Test
    public void transform_empty() throws Exception {
        DomainStoryList domainStoryList = new DomainStoryList();

        StoryItemList storyItemList = mapper.transform(domainStoryList);

        assertThat(storyItemList.getItemList()).isEmpty();
    }

    @Test
    public void transform() throws Exception {
        DomainStoryList domainStoryList = DomainStoryList.ofGroup(
                Arrays.asList(
                        new DomainStoryList.Group(2017, 3,
                                Arrays.asList(
                                        new DomainStory(1, "title1", "content1", dateFormat.parse("2017-03-01 09:00:00"),
                                                Arrays.asList("image1", "image2")),
                                        new DomainStory(2, "title2", "content2", dateFormat.parse("2017-03-01 08:50:10"),
                                                Arrays.asList("image3", "image4"))
                                )
                        ),
                        new DomainStoryList.Group(2017, 2,
                                Arrays.asList(
                                        new DomainStory(3, "title3", "content3", dateFormat.parse("2017-02-28 01:40:30"),
                                                Arrays.asList("image5"))
                                )
                        )
                )
        );
        StoryItemList expectedStoryItemList = new StoryItemList(
                Arrays.asList(
                        new StoryItemList.Item(2017, 3, 2),
                        new StoryItemList.Item(
                                new Story(1, "title1", "content1", "2017-03-01 09:00:00", "image1")
                        ),
                        new StoryItemList.Item(
                                new Story(2, "title2", "content2", "2017-03-01 08:50:10", "image3")
                        ),
                        new StoryItemList.Item(2017, 2, 1),
                        new StoryItemList.Item(
                                new Story(3, "title3", "content3", "2017-02-28 01:40:30", "image5")
                        )
                )
        );

        StoryItemList storyItemList = mapper.transform(domainStoryList);

        assertThat(storyItemList).isEqualTo(expectedStoryItemList);
    }
}
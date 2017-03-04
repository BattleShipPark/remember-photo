package com.battleshippark.rememberphoto.domain;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 */

public class StoryList {
    private final List<Group> groupList;

    public StoryList(List<Story> list) {
        groupList = new ArrayList<>();

        group(list);
    }

    public List<Group> getGroupList() {
        return groupList;
    }


    private void group(List<Story> list) {
        List<YearMonth> groupNames = Stream.of(list).map(this::getYearMonth).distinct().toList();
        Map<YearMonth, List<Story>> groups = Stream.of(list).collect(Collectors.groupingBy(this::getYearMonth));
        groupList.addAll(
                Stream.of(groupNames).map(name -> new Group(name.year, name.month, groups.get(name)))
                        .toList()
        );
    }

    private YearMonth getYearMonth(Story story) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(story.getDate());
        return new YearMonth(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
    }

    @Override
    public String toString() {
        return "StoryList{" +
                "groupList=" + groupList +
                '}';
    }

    private static class YearMonth {
        private int year, month;

        private YearMonth(int year, int month) {
            this.year = year;
            this.month = month;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            YearMonth yearMonth = (YearMonth) o;

            if (year != yearMonth.year) return false;
            return month == yearMonth.month;
        }

        @Override
        public int hashCode() {
            int result = year;
            result = 31 * result + month;
            return result;
        }

        @Override
        public String toString() {
            return "YearMonth{" +
                    "year=" + year +
                    ", month=" + month +
                    '}';
        }
    }

    public static class Group {
        private final int year, month;
        private final List<Story> storyList;

        public Group(int year, int month, List<Story> storyList) {
            this.year = year;
            this.month = month;
            this.storyList = storyList;
        }

        public int getYear() {
            return year;
        }

        public int getMonth() {
            return month;
        }

        public List<Story> getStoryList() {
            return storyList;
        }

        @Override
        public String toString() {
            return "Group{" +
                    "year=" + year +
                    ", month=" + month +
                    ", storyList=" + storyList +
                    '}';
        }
    }
}

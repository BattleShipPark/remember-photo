package com.battleshippark.rememberphoto.domain;

import android.support.annotation.VisibleForTesting;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 */

public class DomainStoryList {
    private final List<Group> groupList;

    public DomainStoryList() {
        this.groupList = new ArrayList<>();
    }

    @VisibleForTesting
    public static DomainStoryList of(List<DomainStory> list) {
        DomainStoryList domainStoryList = new DomainStoryList();
        domainStoryList.group(list);
        return domainStoryList;
    }

    @VisibleForTesting
    public static DomainStoryList ofGroup(List<Group> list) {
        DomainStoryList domainStoryList = new DomainStoryList();
        domainStoryList.groupList.addAll(list);
        return domainStoryList;
    }

    public List<Group> getGroupList() {
        return groupList;
    }


    private void group(List<DomainStory> list) {
        List<YearMonth> groupNames = Stream.of(list).map(this::getYearMonth).distinct().toList();
        Map<YearMonth, List<DomainStory>> groups = Stream.of(list).collect(Collectors.groupingBy(this::getYearMonth));
        groupList.addAll(
                Stream.of(groupNames).map(name -> new Group(name.year, name.month, groups.get(name)))
                        .toList()
        );
    }

    private YearMonth getYearMonth(DomainStory story) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(story.getDate());
        return new YearMonth(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DomainStoryList that = (DomainStoryList) o;

        return groupList != null ? groupList.equals(that.groupList) : that.groupList == null;

    }

    @Override
    public String toString() {
        return "DomainStoryList{" +
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
        private final List<DomainStory> storyList;

        public Group(int year, int month, List<DomainStory> storyList) {
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

        public List<DomainStory> getStoryList() {
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

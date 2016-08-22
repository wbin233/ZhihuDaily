package com.example.dell_pc.wbin_zhihudaily.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wbin on 2016/8/22.
 */
public class NewsEntity {
    private String date;
    private ArrayList<StoryEntity> stories;
    private List<TopStoryEntity> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<StoryEntity> getStories() {
        return stories;
    }

    public void setStories(ArrayList<StoryEntity> stories) {
        this.stories = stories;
    }

    public List<TopStoryEntity> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoryEntity> top_stories) {
        this.top_stories = top_stories;
    }
}

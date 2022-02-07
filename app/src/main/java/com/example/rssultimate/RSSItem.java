package com.example.rssultimate;

public class RSSItem {
    public String title;
    public String description;
    public String link;
    public String pubDate;

    public RSSItem(String title) {
        this.title = title;
    }

    public RSSItem() {

    }


    @Override
    public String toString() {
        return title;

    }
}

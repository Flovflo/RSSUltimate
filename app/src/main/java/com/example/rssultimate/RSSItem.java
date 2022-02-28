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

    public RSSItem(String title,String description,String pubDate, String link) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.pubDate = pubDate;
    }

    @Override
    public String toString() {
        return title;

    }

}

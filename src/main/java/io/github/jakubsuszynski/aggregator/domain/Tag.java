package io.github.jakubsuszynski.aggregator.domain;

public class Tag {
    private String id;
    private String tagName;

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    public Tag() {
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}

package io.github.jakubsuszynski.aggregator.domain;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Set;

public class Article {

    @Id
    private String id;
    private String title;
    private String url;
    private String photoUrl;
    private LocalDateTime uploadDate;
    private String author;
    private String language;

    private Set<String> tags;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    private String website;



    public Article() {
    }


    public Article(LocalDateTime uploadDate, String title, String url, String photoUrl, String author, String website, String language) {
        this.uploadDate = uploadDate;
        this.title = title;
        this.url = url;
        this.photoUrl = photoUrl;
        this.author = author;
        this.website = website;
        this.language = language;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", uploadDate=" + uploadDate +
                ", author='" + author + '\'' +
                ", language='" + language + '\'' +
                ", tags=" + tags +
                ", website='" + website + '\'' +
                '}';
    }

}

package io.github.jakubsuszynski.aggregator.domain;

import java.time.LocalDateTime;

public class ArticleBuilder {
    private LocalDateTime uploadDate;
    private String title;
    private String url;
    private String photoUrl;
    private String author;
    private String website;

    public ArticleBuilder setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
        return this;
    }

    public ArticleBuilder setTitle(String title) {
        this.title = title;
        return this;

    }

    public ArticleBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public ArticleBuilder setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        return this;

    }

    public ArticleBuilder setAuthor(String author) {
        this.author = author;
        return this;

    }

    public ArticleBuilder setWebsite(String website) {
        this.website = website;
        return this;

    }

    public Article build() {
        return new Article(uploadDate, title, url, photoUrl, author, website);
    }
}

package io.github.jakubsuszynski.aggregator.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(name = "uploadDate")
    private LocalDateTime uploadDate;
    @Column(name = "title")
    private String title;
    @Column(name = "url")
    private String url;
    @Column(name = "photoUrl")
    private String photoUrl;
    @Column(name = "author")
    private String author;

    @Column(name = "website")
    private String website;

    public Article() {
    }

    public Article(LocalDateTime uploadDate, String title, String url, String photoUrl, String author, String website) {
        this.uploadDate = uploadDate;
        this.title = title;
        this.url = url;
        this.photoUrl = photoUrl;
        this.author = author;
        this.website = website;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Long getId() {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(uploadDate, article.uploadDate) &&
                Objects.equals(title, article.title) &&
                Objects.equals(url, article.url) &&
                Objects.equals(photoUrl, article.photoUrl) &&
                Objects.equals(author, article.author) &&
                Objects.equals(website, article.website);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uploadDate, title, url, photoUrl, author, website);
    }
    @Override
    public String toString() {
        return "Article{" +
                "uploadDate=" + uploadDate +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }
}

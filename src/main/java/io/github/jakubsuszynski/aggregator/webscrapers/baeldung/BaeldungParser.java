package io.github.jakubsuszynski.aggregator.webscrapers.baeldung;

import io.github.jakubsuszynski.aggregator.domain.Article;
import io.github.jakubsuszynski.aggregator.domain.ArticleBuilder;
import io.github.jakubsuszynski.aggregator.webscrapers.structure.Parser;
import io.github.jakubsuszynski.aggregator.webscrapers.utils.TagFinder;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME;

@Component
public class BaeldungParser implements Parser {

    private static final String BAELDUNG = "Baeldung.com";
    private static final String BAELDUNG_PHOTO_URL = "https://www.baeldung.com/wp-content/uploads/2018/09/baeldung-logo_333x302.png";
    private List<Article> parsedArticles = new ArrayList<>();
    private Logger logger = LoggerFactory.getLogger(getClass());
    private BaeldungWebscraper baeldungWebscraper = new BaeldungWebscraper();

    @Autowired
    TagFinder tagFinder;

    @Override
    public List<Article> parseArticles() {
        parsedArticles.clear();
        Elements rawArticles = baeldungWebscraper.fetchRawArticles();
        mapArticles(rawArticles);
        return parsedArticles;
    }

    private void mapArticles(Elements rawArticles) {
        try {
            parsedArticles = rawArticles.stream().map(this::parseSingleArticle)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            logger.error("Problem with parsing article's upload date");
        }
    }

    private Article parseSingleArticle(Element element) {
        Article article = new ArticleBuilder()
                .setAuthor(BAELDUNG)
                .setPhotoUrl(BAELDUNG_PHOTO_URL)
                .setTitle(element.select("title").text())
                .setUrl(element.select("link").text())
                .setWebsite(BAELDUNG)
                .setUploadDate(parseUploadDate(element.select("pubDate").first()))
                .setLanguage("english")
                .build();


        article.setTags(tagFinder.findTagsInTitle(article.getTitle()));
        return article;
    }

    private LocalDateTime parseUploadDate(Element article) {
        LocalDateTime date;
        try {
            String stringDate = article.text();
            date = LocalDateTime.parse(stringDate, RFC_1123_DATE_TIME);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
        return date;
    }
}

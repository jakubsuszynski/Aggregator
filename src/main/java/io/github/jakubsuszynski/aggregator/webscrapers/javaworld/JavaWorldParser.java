package io.github.jakubsuszynski.aggregator.webscrapers.javaworld;

import io.github.jakubsuszynski.aggregator.domain.Article;
import io.github.jakubsuszynski.aggregator.domain.ArticleBuilder;
import io.github.jakubsuszynski.aggregator.webscrapers.structure.Parser;
import io.github.jakubsuszynski.aggregator.webscrapers.utils.TagFinder;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

@Component
public class JavaWorldParser implements Parser {

    private static final String JAVAWORLD = "JavaWorld.com";

    @Autowired
    TagFinder tagFinder;
    private JavaWorldWebscraper javaWorldWebscraper = new JavaWorldWebscraper();
    private List<Article> parsedArticles = new ArrayList<>();
    private Logger logger = LoggerFactory.getLogger(getClass());

    public List<Article> parseArticles() {
        parsedArticles.clear();
        Map<Document, String> rawArticles = javaWorldWebscraper.fetchRawArticles();
        mapArticles(rawArticles);
        return parsedArticles;
    }

    private void mapArticles(Map<Document, String> rawArticles) {

        try {
            parsedArticles = rawArticles.entrySet().stream().map(this::parseSingleArticle)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            logger.error("Problem with parsing article's upload date");
        }
    }

    private Article parseSingleArticle(Map.Entry<Document, String> i) {
        Article article = new ArticleBuilder()
                .setAuthor(i.getKey().select("span.fn").text())
                .setPhotoUrl(i.getValue())
                .setTitle(i.getKey().select("h1").text())
                .setUrl(i.getKey().select("input[name='url']").attr("value"))
                .setWebsite(JAVAWORLD)
                .setUploadDate(parseUploadDate(i.getKey()))
                .setLanguage("english")
                .build();


        article.setTags(tagFinder.findSomeTags(article.getTitle()));
        return article;
    }


    private LocalDateTime parseUploadDate(Element article) {
        LocalDateTime date;
        try {
            String stringDate = article.select("span.divider").next().attr("content").substring(0, 16) + ":00";
            date = LocalDateTime.parse(stringDate, ISO_LOCAL_DATE_TIME);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
        return date;
    }
}

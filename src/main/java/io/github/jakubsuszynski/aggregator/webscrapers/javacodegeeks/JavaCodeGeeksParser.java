package io.github.jakubsuszynski.aggregator.webscrapers.javacodegeeks;

import io.github.jakubsuszynski.aggregator.domain.Article;
import io.github.jakubsuszynski.aggregator.domain.ArticleBuilder;
import io.github.jakubsuszynski.aggregator.webscrapers.structure.Parser;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME;

@Component
public class JavaCodeGeeksParser implements Parser {
    public static final String JAVACODEGEEKS = "JavaCodeGeeks.com";
//    @Autowired
    JavaCodeGeeksWebscraper javaCodeGeeksWebscraper = new JavaCodeGeeksWebscraper();

    private List<Article> parsedArticles = new ArrayList<>();
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public List<Article> parseArticles() {
        parsedArticles.clear();
        Elements rawArticles = javaCodeGeeksWebscraper.fetchRawArticles();
        mapArticles(rawArticles);
        return parsedArticles;
    }

    private void mapArticles(Elements rawArticles) {
        try {
            parsedArticles = rawArticles.stream().map(i -> parseSingleArticle(i))
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            logger.error("Problem with parsing article's upload date");
        }
    }

    private Article parseSingleArticle(Element i) {

        return new ArticleBuilder()
                .setAuthor(i.select(".post-meta-author").select("a").text())
                .setPhotoUrl(i.select("img").attr("src"))
                .setTitle(i.select("h2").select("a").text())
                .setUrl(i.select("h2").select("a").attr("href"))
                .setWebsite(JAVACODEGEEKS)
                .setUploadDate(parseUploadDate(i))
                .build();
    }

    private LocalDateTime parseUploadDate(Element i) {
        String date = i.select(".tie-date").text();
        date = date.replace("th", "");
        date = date.replace("st", "");
        date = date.replace("nd", "");
        date = date.replace("rd", "");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        return  LocalDate.parse(date, dateTimeFormatter).atStartOfDay();

    }
}

package io.github.jakubsuszynski.aggregator.webscrapers.mkyong;

import io.github.jakubsuszynski.aggregator.domain.Article;
import io.github.jakubsuszynski.aggregator.domain.ArticleBuilder;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

public class MkyongParser {


    private static final String MKYONG = "Mkyong.com";

    private Logger logger = LoggerFactory.getLogger(MkyongParser.class);
    private List<Article> parsedArticles = new ArrayList<>();

    public List<Article> parseArticles() {
        parsedArticles.clear();
        MkyongWebscraper mkyongWebscraper = new MkyongWebscraper();
        Optional<Elements> rawArticles = mkyongWebscraper.fetchRawArticles();

        rawArticles.ifPresent(this::mapArticles);
        return parsedArticles;
    }

    private void mapArticles(Elements rawArticles) {
        try {
            parsedArticles = rawArticles.stream().map(i -> new ArticleBuilder()
                    .setAuthor(i.select("time").prev().text())
                    .setPhotoUrl(i.select("img").attr("src"))
                    .setTitle(i.select("h4").text())
                    .setUrl(i.select("h4").select("a").attr("href"))
                    .setWebsite(MKYONG)
                    .setUploadDate(parseUploadDate(i))
                    .build())
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            logger.error("Problem with parsing article's upload date");
        }
    }

    private LocalDateTime parseUploadDate(Element article) {
        LocalDateTime date;
        try {
            String stringDate = article.select("time").attr("datetime");
            date = LocalDateTime.parse(stringDate, ISO_OFFSET_DATE_TIME);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
        return date;
    }
}

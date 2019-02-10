package io.github.jakubsuszynski.aggregator.webscrapers.mkyong;

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

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

@Component
public class MkyongParser implements Parser {
    @Autowired
    TagFinder tagFinder;

    private static final String MKYONG = "Mkyong.com";
    private MkyongWebscraper mkyongWebscraper = new MkyongWebscraper();
    private Logger logger = LoggerFactory.getLogger(getClass());
    private List<Article> parsedArticles = new ArrayList<>();

    public List<Article> parseArticles() {
        parsedArticles.clear();
        Elements rawArticles = mkyongWebscraper.fetchRawArticles();
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

    private Article parseSingleArticle(Element i) {
        Article article = new ArticleBuilder()
                .setAuthor(i.select("time").prev().text())
                .setPhotoUrl(i.select("img").attr("src"))
                .setTitle(i.select("h4").text())
                .setUrl(i.select("h4").select("a").attr("href"))
                .setWebsite(MKYONG)
                .setUploadDate(parseUploadDate(i))
                .setLanguage("english")
                .build();

        article.setTags(tagFinder.findTagsInTitle(article.getTitle()));
        return article;
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

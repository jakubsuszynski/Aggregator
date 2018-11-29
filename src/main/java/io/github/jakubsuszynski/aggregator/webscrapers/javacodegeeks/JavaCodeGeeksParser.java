package io.github.jakubsuszynski.aggregator.webscrapers.javacodegeeks;

import io.github.jakubsuszynski.aggregator.domain.Article;
import io.github.jakubsuszynski.aggregator.webscrapers.structure.Parser;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JavaCodeGeeksParser implements Parser {
    @Autowired
    JavaCodeGeeksWebscraper javaCodeGeeksWebscraper;

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

    private Object parseSingleArticle(Element i) {
    }
}

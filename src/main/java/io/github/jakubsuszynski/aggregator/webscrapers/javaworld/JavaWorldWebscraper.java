package io.github.jakubsuszynski.aggregator.webscrapers.javaworld;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JavaWorldWebscraper {

    private static final String JAVAWORLD = "JavaWorld.com";
    public static final String JAVAWORLD_URL = "https://www.javaworld.com";
    private Logger logger = LoggerFactory.getLogger(JavaWorldWebscraper.class);

    public List<String> findUrlsToArticles() {


        Optional<Document> page = getHTMLSourceCode();


        return fetchArticlesUrls(page);
    }

    private Optional<Document> getHTMLSourceCode() {
        Optional<Document> page = Optional.empty();
        try {
            page = Optional.ofNullable(Jsoup.connect(JAVAWORLD_URL).timeout(6000).get());
        } catch (IOException e) {
            logger.warn("Connection to " + JAVAWORLD + " failed");
        }
        return page;
    }

    private List<String> fetchArticlesUrls(Optional<Document> page) {
        List<String> urlsList = new ArrayList<>();
        if (page.isPresent()) {
            urlsList = page.get().getElementsByClass("river-well article").select("figure").select("a").stream()
                    .map(i -> JAVAWORLD_URL + i.attr("href")).collect(Collectors.toList());
        }
        if (!urlsList.isEmpty()) {
            logger.info("No articles found on " + JAVAWORLD);
        }
        return urlsList;
    }

}

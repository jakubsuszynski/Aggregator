package io.github.jakubsuszynski.aggregator.webscrapers.javaworld;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JavaWorldWebscraper {

    private static final String JAVAWORLD = "JavaWorld.com";
    private static final String JAVAWORLD_URL = "https://www.javaworld.com";
    private Logger logger = LoggerFactory.getLogger(JavaWorldWebscraper.class);

    public List<Document> fetchRawArticles() {

        List<String> urls = findUrlsToArticles();

        return addArticlesToList(urls);
    }

    private List<String> findUrlsToArticles() {
        return fetchArticlesUrls(getHTMLSourceCode());
    }

    private List<String> fetchArticlesUrls(Document page) {
        Map<String, String> urlAndPhotoUrl = page.getElementsByClass("river-well article").select("figure").select("a").stream()
                .collect(Collectors.toMap(i->JAVAWORLD_URL + i.attr("href"), i->JAVAWORLD_URL + i.select("img").attr("src")));

        List<String> urlsList = page.getElementsByClass("river-well article").select("figure").select("a").stream()
                .map(i -> JAVAWORLD_URL + i.attr("href")).collect(Collectors.toList());

        if (!urlsList.isEmpty()) {
            logger.info("No articles found on " + JAVAWORLD);
        }
        return urlsList;
    }

    private Document getHTMLSourceCode() {
        Document page = new Document("");
        try {
            page = Jsoup.connect(JAVAWORLD_URL).timeout(6000).get();
        } catch (IOException e) {
            logger.warn("Connection to " + JAVAWORLD + " failed");
        }
        return page;
    }

    private List<Document> addArticlesToList(List<String> urls) {
        List<Document> rawArticles = new ArrayList<>();
        for (String url : urls) {
            try {
                rawArticles.add(Jsoup.connect(url).timeout(6000).get());
            } catch (IOException e) {
                logger.warn("Connection to " + url + " failed");
            }
        }
        return rawArticles;
    }


}

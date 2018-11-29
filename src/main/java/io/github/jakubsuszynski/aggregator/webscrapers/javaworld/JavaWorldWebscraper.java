package io.github.jakubsuszynski.aggregator.webscrapers.javaworld;

import io.github.jakubsuszynski.aggregator.webscrapers.structure.Webscraper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JavaWorldWebscraper extends Webscraper {

    private static final String JAVAWORLD_URL = "https://www.javaworld.com";
    private Logger logger = LoggerFactory.getLogger(getClass());

    public Map<Document, String> fetchRawArticles() {

        Map<String, String> urls = findUrlsToArticles();
        logIfFetched(urls);
        return addArticlesToMap(urls);
    }

    private Map<String, String> findUrlsToArticles() {
        return fetchArticlesUrls(getHTMLSourceCode(JAVAWORLD_URL));
    }

    private Map<String, String> fetchArticlesUrls(Document page) {
        Map<String, String> urlAndPhotoUrlMap = page.getElementsByClass("river-well article").select("figure").select("a").stream()
                .collect(Collectors.toMap(i -> i.attr("abs:href"), i -> i.select("img").attr("abs:data-original")));

        if (urlAndPhotoUrlMap.isEmpty()) {
            logger.info(String.format("No articles found on %s", JAVAWORLD_URL));
        }
        return urlAndPhotoUrlMap;
    }

    private void logIfFetched(Map<String, String> urls) {
        if (!urls.isEmpty()) {
            logger.info(String.format("Articles from %s fetched", JAVAWORLD_URL));
        }
    }


    private Map<Document, String> addArticlesToMap(Map<String, String> urls) {
        Map<Document, String> documentAndPhotoUrl = new HashMap<>();

        for (Map.Entry<String, String> url : urls.entrySet()) {
            try {
                documentAndPhotoUrl.put(Jsoup.connect(url.getKey()).timeout(6000).get(), url.getValue());
            } catch (IOException e) {
                logger.warn(String.format("Connection to %s failed", url));
            }
        }
        return documentAndPhotoUrl;
    }


}

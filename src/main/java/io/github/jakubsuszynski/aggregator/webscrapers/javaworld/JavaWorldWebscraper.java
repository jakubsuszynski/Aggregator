package io.github.jakubsuszynski.aggregator.webscrapers.javaworld;

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
public class JavaWorldWebscraper {

    private static final String JAVAWORLD = "JavaWorld.com";
    private static final String JAVAWORLD_URL = "https://www.javaworld.com";
    private Logger logger = LoggerFactory.getLogger(JavaWorldWebscraper.class);

    public Map<Document, String> fetchRawArticles() {

        Map<String, String> urls = findUrlsToArticles();

        return addArticlesToList(urls);
    }

    private Map<String, String> findUrlsToArticles() {
        return fetchArticlesUrls(getHTMLSourceCode());
    }

    private Map<String, String> fetchArticlesUrls(Document page) {
        Map<String, String> urlAndPhotoUrlMap = page.getElementsByClass("river-well article").select("figure").select("a").stream()
                .collect(Collectors.toMap(i -> i.attr("abs:href"), i -> i.select("img").attr("abs:data-original")));

        if (urlAndPhotoUrlMap.isEmpty()) {
            logger.info("No articles found on " + JAVAWORLD);
        }
        return urlAndPhotoUrlMap;
    }

    private Document getHTMLSourceCode() {
        Document page = new Document("");
        try {
            page = Jsoup.connect(JAVAWORLD_URL).timeout(6000).get();
        } catch (IOException e) {
            logger.warn(String.format("Connection to %s failed", JAVAWORLD));
        }
        return page;
    }

    private Map<Document, String> addArticlesToList(Map<String, String> urls) {
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

package io.github.jakubsuszynski.aggregator.webscrapers.structure;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.stream.Collectors;

public abstract class Webscraper {
    private Logger logger = LoggerFactory.getLogger(getClass());


    protected Document getHTMLSourceCode(String pageUrl) {
        Document page = new Document("");
        try {
            page = Jsoup.connect(pageUrl).timeout(6000).get();
        } catch (IOException e) {
            logger.warn(String.format("Connection to %s failed", pageUrl));
        }
        return page;
    }

    protected void logIfFetched(Elements articlesInXML, String pageUrl) {
        if (!articlesInXML.isEmpty()) {
            logger.info(String.format("Articles from %s fetched", pageUrl));
        }
    }

    protected Elements getXmlArticles(Document page, String selector, String pageUrl) {
        Elements xmlArticles = page.select(selector).stream().limit(5).collect(Collectors.toCollection(Elements::new));
        if (xmlArticles.isEmpty()) {
            logger.info(String.format("No articles found on %s", pageUrl));
        }
        return xmlArticles;
    }
}

package io.github.jakubsuszynski.aggregator.webscrapers.javacodegeeks;

import io.github.jakubsuszynski.aggregator.webscrapers.structure.Webscraper;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaCodeGeeksWebscraper extends Webscraper {
    public static final String JAVACODEGEEKS_URL = "https://www.javacodegeeks.com/category/java";
    private Logger logger = LoggerFactory.getLogger(getClass());

    public Elements fetchRawArticles() {
        Document page = getHTMLSourceCode(JAVACODEGEEKS_URL);
        Elements rawArticles = getXmlArticles(page);
        logIfFetched(rawArticles);
        return rawArticles;

    }

    private Elements getXmlArticles(Document page) {
        Elements xmlArticles = page.select("article");
        if (xmlArticles.isEmpty()) {
            logger.info(String.format("No articles found on %s", JAVACODEGEEKS_URL));
        }
        return xmlArticles;
    }

    private void logIfFetched(Elements articlesInXML) {
        if (!articlesInXML.isEmpty()) {
            logger.info(String.format("Articles from %s fetched", JAVACODEGEEKS_URL));
        }
    }
}

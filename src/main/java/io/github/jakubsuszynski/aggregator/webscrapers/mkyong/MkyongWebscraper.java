package io.github.jakubsuszynski.aggregator.webscrapers.mkyong;

import io.github.jakubsuszynski.aggregator.webscrapers.structure.Webscraper;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//@Component
public class MkyongWebscraper extends Webscraper {


    private static final String MKYONG_URL = "https://www.mkyong.com/";
    private Logger logger = LoggerFactory.getLogger(getClass());


    public Elements fetchRawArticles() {


        Document page = getHTMLSourceCode(MKYONG_URL);
        Elements rawArticles = getXmlArticles(page);
        logIfFetched(rawArticles);
        return rawArticles;
    }

    private Elements getXmlArticles(Document page) {
        Elements xmlArticles = page.select("article");
        if (xmlArticles.isEmpty()) {
            logger.info(String.format("No articles found on %s", MKYONG_URL));
        }
        return xmlArticles;
    }

    private void logIfFetched(Elements articlesInXML) {
        if (!articlesInXML.isEmpty()) {
            logger.info(String.format("Articles from %s fetched", MKYONG_URL));
        }
    }

}

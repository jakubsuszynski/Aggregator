package io.github.jakubsuszynski.aggregator.webscrapers.structure;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public abstract class Webscraper {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public Document getHTMLSourceCode(String url) {
        Document page = new Document("");
        try {
            page = Jsoup.connect(url).timeout(6000).get();
        } catch (IOException e) {
            logger.warn(String.format("Connection to %s failed", url));
        }
        return page;
    }
}

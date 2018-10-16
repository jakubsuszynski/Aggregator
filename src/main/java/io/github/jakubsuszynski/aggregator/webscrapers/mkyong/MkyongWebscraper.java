package io.github.jakubsuszynski.aggregator.webscrapers.mkyong;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class MkyongWebscraper {


    private static final String MKYONG = "Mkyong.com";
    private Logger logger = LoggerFactory.getLogger(MkyongWebscraper.class);


    public Optional<Elements> fetchRawArticles() {

        Optional<Elements> rawArticles = Optional.empty();
        Optional<Document> page = getHTMLSourceCode();
        if (page.isPresent()) {
            Elements articlesInXML = getXmlArticles(page.get());

            logIfFetched(articlesInXML);
            rawArticles = Optional.of(articlesInXML);
        }
        return rawArticles;
    }


    private Optional<Document> getHTMLSourceCode() {
        Optional<Document> page = Optional.empty();
        try {
            page = Optional.ofNullable(Jsoup.connect("https://www.google.com/").timeout(6000).get());
        } catch (IOException e) {
            logger.warn("Connection to " + MKYONG + " failed");
        }
        return page;
    }

    private Elements getXmlArticles(Document page) {
        Elements xmlArticles = page.select("article");
        if (xmlArticles.isEmpty()) {
            logger.info("No articles found on " + MKYONG);
        }
        return xmlArticles;
    }

    private void logIfFetched(Elements articlesInXML) {
        if (!articlesInXML.isEmpty()) {
            logger.info("Articles from Mkyong fetched");
        }
    }

}

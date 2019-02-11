package io.github.jakubsuszynski.aggregator.webscrapers.baeldung;

import io.github.jakubsuszynski.aggregator.webscrapers.structure.Webscraper;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

class BaeldungWebscraper extends Webscraper {
    private static final String PAGE_URL = "http://feeds.feedburner.com/Baeldung";

    Elements fetchRawArticles() {
        Document page = getHTMLSourceCode(PAGE_URL);
        Elements rawArticles = getXmlArticles(page, "item", PAGE_URL);
        logIfFetched(rawArticles, PAGE_URL);
        return rawArticles;

    }


}

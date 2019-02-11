package io.github.jakubsuszynski.aggregator.webscrapers.mkyong;

import io.github.jakubsuszynski.aggregator.webscrapers.structure.Webscraper;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

class MkyongWebscraper extends Webscraper {


    private static final String PAGE_URL = "https://www.mkyong.com/";


    Elements fetchRawArticles() {
        Document page = getHTMLSourceCode(PAGE_URL);
        Elements rawArticles = getXmlArticles(page, "article", PAGE_URL);
        logIfFetched(rawArticles, PAGE_URL);
        return rawArticles;
    }


}

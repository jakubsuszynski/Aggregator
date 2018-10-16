package io.github.jakubsuszynski.aggregator.webscrapers;

import io.github.jakubsuszynski.aggregator.domain.Article;
import io.github.jakubsuszynski.aggregator.domain.ArticleBuilder;
import io.github.jakubsuszynski.aggregator.service.ArticlesService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

@Component
public class MkyongWebScraper implements WebScraper {

    @Autowired
    ArticlesService articlesService;


    private static final String MKYONG = "Mkyong.com";
    private Logger logger = LoggerFactory.getLogger(MkyongWebScraper.class);
    private List<Article> articlesList = new ArrayList<>();

    @Override
    public List<Article> getArticlesList() {
        fetchArticles();
        return articlesList;
    }


    private void fetchArticles() {

        clearArticlesList();
        Optional<Document> page = getHTMLSourceCode();

        if (page.isPresent()) {
            Elements articlesInXML = getXmlArticles(page.get());

            logIfFetched(articlesInXML);

            fillUniqueArticlesList(articlesInXML);

        }
    }

    private void clearArticlesList() {
        articlesList.clear();
    }

    private Optional<Document> getHTMLSourceCode() {
        Optional<Document> page = Optional.empty();
        try {
            page = Optional.ofNullable(Jsoup.connect("https://www.mkyong.com/").timeout(6000).get());
        } catch (IOException e) {
            logger.warn("Connection to Mkyong failed");
        }
        return page;
    }

    private Elements getXmlArticles(Document page) {
        return page.select("article");
    }

    private void logIfFetched(Elements articlesInXML) {
        if (!articlesInXML.isEmpty()) {
            logger.info("Articles from Mkyong fetched");
        }
    }

    private void fillUniqueArticlesList(Elements articlesInXML) {
        try {
            articlesList = articlesInXML.stream().map(i -> new ArticleBuilder()
                    .setAuthor(i.select("time").prev().text())
                    .setPhotoUrl(i.select("img").attr("src"))
                    .setTitle(i.select("h4").text())
                    .setUrl(i.select("h4").select("a").attr("href"))
                    .setWebsite(MKYONG)
                    .setUploadDate(parseUploadDate(i))
                    .build()
            )
                    .filter(this::isNotInDatabase)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            logger.warn("Problem with parsing an article's LocalDateTime on Mkyong.com");
        } catch (Exception e) {
            logger.warn("Problem with parsing articles from Mkyong.com");
        }
    }

    private LocalDateTime parseUploadDate(Element article) {
        LocalDateTime date;
        try {
            String stringDate = article.select("time").attr("datetime");
            date = LocalDateTime.parse(stringDate, ISO_OFFSET_DATE_TIME);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
        return date;
    }

    private Boolean isNotInDatabase(Article i) {
        return !articlesService.checkIfExistsByUploadDateAndTitle(i);
    }


}

package io.github.jakubsuszynski.aggregator.webscrapers;

import io.github.jakubsuszynski.aggregator.domain.Article;
import io.github.jakubsuszynski.aggregator.service.ArticlesService;
import io.github.jakubsuszynski.aggregator.webscrapers.javaworld.JavaWorldParser;
import io.github.jakubsuszynski.aggregator.webscrapers.mkyong.MkyongParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataSaver {

    @Autowired
    MkyongParser mkyongParser;
    @Autowired
    JavaWorldParser javaWorldParser;

    @Autowired
    ArticlesService articlesService;

    private Logger logger = LoggerFactory.getLogger(DataSaver.class);

    List<Article> uniqueArticles = new ArrayList<>();

    public void saveFetchedArticles() {

        scanMkyong();
        scanJavaWorld();

        uniqueArticles = uniqueArticles.stream()
                .filter(this::isPresentInDatabase)
                .collect(Collectors.toList());

        articlesService.saveArticles(uniqueArticles);

        if (!uniqueArticles.isEmpty()) {
            logger.info(String.format("%d new articles saved", uniqueArticles.size()));
        }

    }


    private void scanMkyong() {
        uniqueArticles.addAll(mkyongParser.parseArticles());
    }

    private void scanJavaWorld() {
        uniqueArticles.addAll(javaWorldParser.parseArticles());
    }

    private boolean isPresentInDatabase(Article i) {
        return !articlesService.checkIfExistsByUploadDateAndTitle(i);
    }
}

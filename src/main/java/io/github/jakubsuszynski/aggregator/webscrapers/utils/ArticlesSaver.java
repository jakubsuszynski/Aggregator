package io.github.jakubsuszynski.aggregator.webscrapers.utils;

import io.github.jakubsuszynski.aggregator.domain.Article;
import io.github.jakubsuszynski.aggregator.service.ArticlesService;
import io.github.jakubsuszynski.aggregator.webscrapers.structure.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArticlesSaver {

    private ArticlesService articlesService;

    private List<Parser> parsers;

    @Autowired
    public ArticlesSaver(ArticlesService articlesService, List<Parser> parsers) {
        this.articlesService = articlesService;
        this.parsers = parsers;
    }

    private Logger logger = LoggerFactory.getLogger(getClass());

    private List<Article> uniqueArticles = new ArrayList<>();

    public List<Article> saveFetchedArticles() {


        parsers.forEach(i -> uniqueArticles.addAll(i.parseArticles()));

        uniqueArticles = uniqueArticles.stream()
                .filter(i->articlesService.isUnique(i))
                .collect(Collectors.toList());

        articlesService.saveArticles(uniqueArticles);

        logger.info(String.format("%d new articles saved", uniqueArticles.size()));

        return uniqueArticles;
    }



}

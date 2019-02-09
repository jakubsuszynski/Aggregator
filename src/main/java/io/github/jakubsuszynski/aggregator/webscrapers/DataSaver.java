package io.github.jakubsuszynski.aggregator.webscrapers;

import io.github.jakubsuszynski.aggregator.domain.Article;
import io.github.jakubsuszynski.aggregator.service.ArticlesService;
import io.github.jakubsuszynski.aggregator.webscrapers.javacodegeeks.JavaCodeGeeksParser;
import io.github.jakubsuszynski.aggregator.webscrapers.javaworld.JavaWorldParser;
import io.github.jakubsuszynski.aggregator.webscrapers.mkyong.MkyongParser;
import io.github.jakubsuszynski.aggregator.webscrapers.structure.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataSaver {

    @Autowired
    private MkyongParser mkyongParser;
    @Autowired
    private JavaWorldParser javaWorldParser;
    @Autowired
    private JavaCodeGeeksParser javaCodeGeeksParser;
    @Autowired
    ArticlesService articlesService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private List<Article> uniqueArticles = new ArrayList<>();

    public List<Article> saveFetchedArticles() {

        List<Parser> parsers = Arrays.asList(mkyongParser, javaCodeGeeksParser, javaWorldParser);

        parsers.forEach(i -> uniqueArticles.addAll(i.parseArticles()));

        uniqueArticles = uniqueArticles.stream()
                .filter(this::isUnique)
                .collect(Collectors.toList());

        articlesService.saveArticles(uniqueArticles);

        logger.info(String.format("%d new articles saved", uniqueArticles.size()));

        return uniqueArticles;
    }


    private boolean isUnique(Article i) {
        return !articlesService.checkIfExistsByUploadDateAndTitle(i);
    }
}

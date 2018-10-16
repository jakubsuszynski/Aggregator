package io.github.jakubsuszynski.aggregator.webscrapers;

import io.github.jakubsuszynski.aggregator.domain.Article;
import io.github.jakubsuszynski.aggregator.service.ArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MkyongDataProvider {

    @Autowired
    ArticlesService articlesService;

    @Autowired
    MkyongParser mkyongParser;


    public void saveArticles() {

        List<Article> uniqueArticles = mkyongParser.parseArticles().stream()
                .filter(i -> articlesService.checkIfExistsByUploadDateAndTitle(i))
                .collect(Collectors.toList());

        articlesService.saveArticles(uniqueArticles);

    }
}

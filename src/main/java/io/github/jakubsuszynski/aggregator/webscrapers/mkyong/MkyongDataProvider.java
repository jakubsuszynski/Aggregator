package io.github.jakubsuszynski.aggregator.webscrapers.mkyong;

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


    public void saveArticles() {
        MkyongParser mkyongParser = new MkyongParser();

        List<Article> uniqueArticles = mkyongParser.parseArticles().stream()
                .filter(this::isPresentInDatabase)
                .collect(Collectors.toList());

        articlesService.saveArticles(uniqueArticles);

    }

    private boolean isPresentInDatabase(Article i) {
        return !articlesService.checkIfExistsByUploadDateAndTitle(i);
    }
}

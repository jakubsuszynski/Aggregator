package io.github.jakubsuszynski.aggregator.webscrapers.mkyong;

import io.github.jakubsuszynski.aggregator.domain.Article;
import io.github.jakubsuszynski.aggregator.service.ArticlesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MkyongDataProvider {

    @Autowired
    ArticlesService articlesService;
    private Logger logger = LoggerFactory.getLogger(MkyongDataProvider.class);


    public void saveArticles() {
        MkyongParser mkyongParser = new MkyongParser();

        List<Article> uniqueArticles = mkyongParser.parseArticles().stream()
                .filter(this::isPresentInDatabase)
                .collect(Collectors.toList());

        articlesService.saveArticles(uniqueArticles);

        if (!uniqueArticles.isEmpty()) {
            logger.info(uniqueArticles.size() + "new articles from Mkyong saved");
        }
    }

    private boolean isPresentInDatabase(Article i) {
        return !articlesService.checkIfExistsByUploadDateAndTitle(i);
    }
}

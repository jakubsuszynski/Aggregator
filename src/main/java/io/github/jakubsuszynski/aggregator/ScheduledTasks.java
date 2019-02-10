package io.github.jakubsuszynski.aggregator;

import io.github.jakubsuszynski.aggregator.webscrapers.utils.ArticlesSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    ArticlesSaver articlesSaver;

//    @Scheduled(cron = "* * * * * *")
    public void addArticle() {
        articlesSaver.saveFetchedArticles();
    }
}

package io.github.jakubsuszynski.aggregator;

import io.github.jakubsuszynski.aggregator.service.ArticlesService;
import io.github.jakubsuszynski.aggregator.webscrapers.MkyongWebScraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    @Autowired
    ArticlesService articlesService;

    @Autowired
    MkyongWebScraper mkyongWebScraper;


    @Scheduled(cron = "* * * * * *")
    public void addArticle() {
        articlesService.saveArticles(mkyongWebScraper.getArticlesList());
    }

}

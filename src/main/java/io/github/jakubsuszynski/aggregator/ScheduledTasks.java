package io.github.jakubsuszynski.aggregator;

import io.github.jakubsuszynski.aggregator.domain.Article;
import io.github.jakubsuszynski.aggregator.service.ArticlesService;
import io.github.jakubsuszynski.aggregator.service.TagsService;
import io.github.jakubsuszynski.aggregator.webscrapers.DataSaver;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    DataSaver dataSaver;
    @Autowired
    TagsService tagsService;
    @Autowired
    ArticlesService articlesService;

    @Scheduled(cron = "* * * * * *")
    public void addArticle() {
        dataSaver.saveFetchedArticles();

    }


}

package io.github.jakubsuszynski.aggregator;

import io.github.jakubsuszynski.aggregator.webscrapers.DataSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    DataSaver dataSaver;

    @Scheduled(cron = "* * * * * *")
    public void addArticle() {
        dataSaver.saveFetchedArticles();
    }

}

package io.github.jakubsuszynski.aggregator;

import io.github.jakubsuszynski.aggregator.webscrapers.mkyong.MkyongDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    MkyongDataProvider mkyongDataProvider;


    @Scheduled(cron = "* * * * * *")
    public void addArticle() {
//        mkyongDataProvider.saveArticles();
    }

}

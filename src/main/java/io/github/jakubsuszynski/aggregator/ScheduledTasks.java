package io.github.jakubsuszynski.aggregator;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {


    @Scheduled(cron = "* * * * * *")
    public void addArticle() {
    }

}

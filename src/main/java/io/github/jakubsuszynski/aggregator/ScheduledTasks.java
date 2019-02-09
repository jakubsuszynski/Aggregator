package io.github.jakubsuszynski.aggregator;

import io.github.jakubsuszynski.aggregator.webscrapers.DataSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ScheduledTasks {

    @Autowired
    DataSaver dataSaver;

//    @Scheduled(cron = "* * * * * *")
//    public void addArticle() {
//        dataSaver.saveFetchedArticles();
//    }




}

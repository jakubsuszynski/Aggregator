package io.github.jakubsuszynski.aggregator;

import io.github.jakubsuszynski.aggregator.domain.Tag;
import io.github.jakubsuszynski.aggregator.service.TagsService;
import io.github.jakubsuszynski.aggregator.webscrapers.DataSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ScheduledTasks {

    @Autowired
    DataSaver dataSaver;
    @Autowired
    TagsService tagsService;

    @Scheduled(cron = "* * * * * *")
    public void addArticle() {
        dataSaver.saveFetchedArticles();
    }

    private void addSomeTags(){
        List<String> tags = Arrays.asList("java", "orm", "hibernate", "angular", "react", "html", "css"
                , "jpa", "jdbc", "kotlin", "scala", "docker", "rest", "spring", "boot", "jenkins", "sql", "db", "database",
                "framework", "jee", "jse", "jdk", "sdk", "jre", "jvm", "garbage collector", "web", "guava", "lists", "arrays",
                "generics", "primitive", "git", "regex", "maven", "gradle", "jhipster", "eclipse", "jetbrain", "intellij",
                "jboss", "wildfly", "tomcat", "security", "datasource", "javascript", "python", "jsp", "jsf", "soap",
                "algorithm", "kubernetes", "ubuntu", "linux", "golang", "javascript", "jquery");

        Set<Tag> parsedTags = tags.stream().map(Tag::new).collect(Collectors.toSet());
        tagsService.saveAll(parsedTags);
    }


}

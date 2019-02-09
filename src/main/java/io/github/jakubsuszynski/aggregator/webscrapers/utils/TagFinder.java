package io.github.jakubsuszynski.aggregator.webscrapers.utils;

import io.github.jakubsuszynski.aggregator.domain.Tag;
import io.github.jakubsuszynski.aggregator.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TagFinder {
    @Autowired
    TagsService tagsService;
    private List<String> tags;

    public TagFinder() {
        //perform once only if database collection is empty
//        tags = Arrays.asList("java", "orm", "hibernate", "angular", "react", "html", "css"
//                , "jpa", "jdbc", "kotlin", "scala", "docker", "rest", "spring", "boot", "jenkins", "sql", "db", "database",
//                "framework", "jee", "jse", "jdk", "sdk", "jre", "jvm", "garbage collector", "web", "guava", "lists", "arrays",
//                "generics", "primitive", "git", "regex", "maven", "gradle", "jhipster", "eclipse", "jetbrain", "intellij",
//                "jboss", "wildfly", "tomcat", "security", "datasource", "javascript", "python", "jsp", "jsf", "soap",
//                "algorithm", "kubernetes", "ubuntu", "linux", "golang", "javascript", "jquery");

    }

    @PostConstruct
    public void init() {
        this.tags =
                tagsService.getAllTags().stream().map(Tag::getTagName).
                        collect(Collectors.toList());
    }

    public void saveBasicTagsToDatabase() {
        tagsService.saveAll(tags.stream().map(Tag::new).collect(Collectors.toList()));
    }

    public Set<String> findSomeTags(String title) {
        return this.tags.stream().filter(title.toLowerCase()::contains).collect(Collectors.toSet());
    }


}

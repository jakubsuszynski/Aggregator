package io.github.jakubsuszynski.aggregator.webscrapers.utils;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TagFinder {
    List<String> tags;

    public TagFinder() {
        tags = Arrays.asList("java", "orm", "hibernate", "angular", "react", "html", "css"
                , "jpa", "jdbc", "kotlin", "scala", "docker", "rest", "spring", "boot", "jenkins", "sql", "db", "database",
                "framework", "jee", "jse", "jdk", "sdk", "jre", "jvm", "garbage collector", "web", "guava", "lists", "arrays",
                "generics", "primitive", "git", "regex", "maven", "gradle", "jhipster", "eclipse", "jetbrain", "intellij",
                "jboss", "wildfly", "tomcat", "security", "datasource", "javascript", "python", "jsp", "jsf", "soap",
                "algorithm", "kubernetes", "ubuntu", "linux", "golang", "javascript", "jquery");

    }

    public Set<String> findSomeTags(String title) {

        return this.tags.stream().filter(title.toLowerCase()::contains).collect(Collectors.toSet());

    }


}

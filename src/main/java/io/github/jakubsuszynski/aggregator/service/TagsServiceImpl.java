package io.github.jakubsuszynski.aggregator.service;

import io.github.jakubsuszynski.aggregator.domain.Tag;
import io.github.jakubsuszynski.aggregator.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TagsServiceImpl implements TagsService {
    @Autowired
    TagsRepository tagsRepository;


    @Override
    public List<Tag> getAllTags() {
        return tagsRepository.findAll();
    }

    @Override
    public void saveAll(List<Tag> tags) {
        tagsRepository.saveAll(tags);
    }

    @Override
    public void addTag(Tag tag) {
        tagsRepository.save(tag);
    }

    @Override
    public void saveBasicTags() {
        List<Tag> tags = Stream.of("algorithm", "angular", "arrays", "boot", "css", "database", "datasource", "db",
                "docker", "eclipse", "framework", "garbage collector", "generics", "git", "golang", "gradle", "guava",
                "hibernate", "html", "intellij", "java", "javascript", "jboss", "jdbc", "jdk", "jee", "jenkins",
                "jetbrain", "jhipster", "jpa", "jquery", "jre", "jse", "jsf", "jsp", "jvm", "kotlin", "kubernetes",
                "linux", "lists", "maven", "orm", "primitive", "python", "react", "regex", "rest", "scala", "sdk",
                "security", "soap", "spring", "sql", "tomcat", "ubuntu", "web", "wildfly")
                .map(Tag::new)
                .filter(this::checkIfIsUnique)
                .collect(Collectors.toList());

        saveAll(tags);

    }

    @Override
    public boolean checkIfIsUnique(Tag tag) {
        Optional<Tag> optionalTag = Optional.ofNullable(tagsRepository.findByTagName(tag.getTagName()));
        return !optionalTag.isPresent();
    }
}

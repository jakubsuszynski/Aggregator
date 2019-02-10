package io.github.jakubsuszynski.aggregator.webscrapers.utils;

import io.github.jakubsuszynski.aggregator.domain.Tag;
import io.github.jakubsuszynski.aggregator.service.TagsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TagFinder {

    private TagsService tagsService;

    private List<String> tags;

    public TagFinder(TagsService tagsService) {
        this.tagsService = tagsService;
    }

    @PostConstruct
    public void getAllTags() {
        this.tags =
                tagsService.getAllTags().stream().map(Tag::getTagName).
                        collect(Collectors.toList());
    }

    public Set<String> findTagsInTitle(String title) {
        return this.tags.stream().filter(title.toLowerCase()::contains).collect(Collectors.toSet());
    }


}

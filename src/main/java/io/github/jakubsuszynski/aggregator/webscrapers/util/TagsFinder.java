package io.github.jakubsuszynski.aggregator.webscrapers.util;

import io.github.jakubsuszynski.aggregator.domain.Article;
import io.github.jakubsuszynski.aggregator.domain.Tag;
import io.github.jakubsuszynski.aggregator.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class TagsFinder {
    @Autowired
    private TagsService tagsService;

    private List<Tag> tags;


    public Set<Tag> findTagsInTitle(Article article) {
        scanTags();
        Set<Tag> articlesTags = new HashSet<>();

        for (Tag tag : tags) {
            if (article.getTitle().toLowerCase().contains(tag.getTag().toLowerCase())) {
                articlesTags.add(new Tag(tag.getTag()));
            }
        }
        article.setTags(articlesTags);

        return articlesTags;
    }

    private void scanTags() {
        tags = tagsService.getAll();
    }
}

package io.github.jakubsuszynski.aggregator.service;

import io.github.jakubsuszynski.aggregator.domain.Tag;

import java.util.List;

public interface TagsService {
    List<Tag> getAllTags();

    void saveAll(List<Tag> tags);

    void addTag(Tag tag);

    void saveBasicTags();

    boolean checkIfIsUnique(Tag tag);
}

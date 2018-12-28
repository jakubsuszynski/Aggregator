package io.github.jakubsuszynski.aggregator.service;

import io.github.jakubsuszynski.aggregator.domain.Tag;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TagsService {
    Optional<Tag> getById(long id);

    List<Tag> getAll();

    void saveTag(Tag tag);

    void saveAll(Set<Tag> tags);

}

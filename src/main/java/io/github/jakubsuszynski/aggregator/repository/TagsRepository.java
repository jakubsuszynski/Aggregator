package io.github.jakubsuszynski.aggregator.repository;

import io.github.jakubsuszynski.aggregator.domain.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagsRepository extends CrudRepository<Tag, Long> {
    Tag findByTag(String tag);
}

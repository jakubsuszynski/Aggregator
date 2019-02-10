package io.github.jakubsuszynski.aggregator.repository;

import io.github.jakubsuszynski.aggregator.domain.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TagsRepository extends MongoRepository<Tag, String> {
    Tag findByTagName(String tagName);
}

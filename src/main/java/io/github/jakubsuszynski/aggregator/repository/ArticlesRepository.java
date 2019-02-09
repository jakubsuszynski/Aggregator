package io.github.jakubsuszynski.aggregator.repository;

import io.github.jakubsuszynski.aggregator.domain.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticlesRepository extends MongoRepository<Article, String> {
}

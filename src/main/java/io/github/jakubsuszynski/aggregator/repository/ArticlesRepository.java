package io.github.jakubsuszynski.aggregator.repository;

import io.github.jakubsuszynski.aggregator.domain.Article;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface ArticlesRepository extends CrudRepository<Article, Long> {

    Article findByUploadDate(LocalDateTime localDateTime);

    Article findByTitle(String title);

    Article findByTitleAndUploadDate(String title, LocalDateTime uploadDate);
}

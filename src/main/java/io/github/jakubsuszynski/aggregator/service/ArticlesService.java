package io.github.jakubsuszynski.aggregator.service;

import io.github.jakubsuszynski.aggregator.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticlesService {

    List<Article> getAll();

    Optional<Article> getById(Long id);

    void saveArticle(Article article);

    void saveArticles(List<Article> articles);


    Boolean checkIfExistsByUploadDateAndTitle(Article article);
}

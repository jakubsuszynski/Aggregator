package io.github.jakubsuszynski.aggregator.service;

import io.github.jakubsuszynski.aggregator.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticlesService {

    List<Article> getAll();

    Article getById(Long id) throws RuntimeException;

    void saveArticle(Article article);

    void saveArticles(List<Article> articles);


    Boolean checkIfExistsByUploadDateAndTitle(Article article);
}

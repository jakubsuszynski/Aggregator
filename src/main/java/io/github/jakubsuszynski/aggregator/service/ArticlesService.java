package io.github.jakubsuszynski.aggregator.service;

import io.github.jakubsuszynski.aggregator.domain.Article;

import java.util.List;

public interface ArticlesService {
    boolean checkIfExistsByUploadDateAndTitle(Article article);

    void saveArticles(List<Article> uniqueArticles);
}

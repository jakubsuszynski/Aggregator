package io.github.jakubsuszynski.aggregator.webscrapers;

import io.github.jakubsuszynski.aggregator.domain.Article;

import java.util.List;

public interface Parser {
    List<Article> parseArticles();
}

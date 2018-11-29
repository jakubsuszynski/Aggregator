package io.github.jakubsuszynski.aggregator.webscrapers.structure;

import io.github.jakubsuszynski.aggregator.domain.Article;

import java.util.List;

public interface Parser {
    List<Article> parseArticles();
}

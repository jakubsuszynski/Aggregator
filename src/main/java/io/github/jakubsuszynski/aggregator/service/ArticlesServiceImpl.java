package io.github.jakubsuszynski.aggregator.service;

import io.github.jakubsuszynski.aggregator.domain.Article;
import io.github.jakubsuszynski.aggregator.repository.ArticlesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticlesServiceImpl implements ArticlesService {

    @Autowired
    ArticlesRepository articlesRepository;

    @Override
    public boolean checkIfExistsByUploadDateAndTitle(Article article) {
        Optional<Article> optionalArticle = Optional.ofNullable(articlesRepository.findByTitleAndUploadDate(article.getTitle(), article.getUploadDate()));
        return optionalArticle.isPresent();
    }

    @Override
    public void saveArticles(List<Article> uniqueArticles) {
        articlesRepository.saveAll(uniqueArticles);
    }

    @Override
    public boolean isUnique(Article i) {
        return !checkIfExistsByUploadDateAndTitle(i);
    }
}

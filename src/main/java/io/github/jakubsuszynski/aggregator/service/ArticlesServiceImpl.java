package io.github.jakubsuszynski.aggregator.service;

import io.github.jakubsuszynski.aggregator.domain.Article;
import io.github.jakubsuszynski.aggregator.repository.ArticlesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ArticlesServiceImpl implements ArticlesService {

    @Autowired
    ArticlesRepository articlesRepository;

    @Override
    public List<Article> getAll() {
        return (List<Article>) articlesRepository.findAll();
    }

    @Override
    public Optional<Article> getById(Long id) {
        return articlesRepository.findById(id);
    }

    @Override
    public void saveArticle(Article article) {
        articlesRepository.save(article);
    }

    @Override
    public void saveArticles(List<Article> articles) {
        articlesRepository.saveAll(articles);
    }

    @Override
    public Boolean checkIfExistsByUploadDateAndTitle(Article article) {

        Optional<Article> optionalArticle = Optional.ofNullable(articlesRepository.findByTitleAndUploadDate(article.getTitle(), article.getUploadDate()));
        return optionalArticle.isPresent();
    }
}
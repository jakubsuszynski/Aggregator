package io.github.jakubsuszynski.aggregator;

import io.github.jakubsuszynski.aggregator.domain.Article;
import io.github.jakubsuszynski.aggregator.repository.ArticlesRepository;
import io.github.jakubsuszynski.aggregator.webscrapers.DataSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@SpringBootApplication
@EnableScheduling
public class AggregatorApplication implements CommandLineRunner {
    @Autowired
    DataSaver dataSaver;
    @Autowired
    ArticlesRepository articlesRepository;


    public static void main(String[] args) {
        SpringApplication.run(AggregatorApplication.class, args);
    }


    @Override
    public void run(String... args) {
        List<Article> articles = dataSaver.saveFetchedArticles();
//        articles.stream().forEach(i-> System.out.println(i.toString()));
        articlesRepository.saveAll(articles);
    }


}
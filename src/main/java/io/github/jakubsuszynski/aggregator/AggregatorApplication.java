package io.github.jakubsuszynski.aggregator;

import io.github.jakubsuszynski.aggregator.webscrapers.utils.ArticlesSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AggregatorApplication implements CommandLineRunner {

    private ArticlesSaver articlesSaver;

    @Autowired
    public AggregatorApplication(ArticlesSaver articlesSaver) {
        this.articlesSaver = articlesSaver;
    }

    public static void main(String[] args) {
        SpringApplication.run(AggregatorApplication.class, args);
    }

    @Override
    public void run(String... args) {
        articlesSaver.saveFetchedArticles();
    }
}
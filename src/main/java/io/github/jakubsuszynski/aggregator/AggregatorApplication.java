package io.github.jakubsuszynski.aggregator;

import io.github.jakubsuszynski.aggregator.webscrapers.DataSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableScheduling
public class AggregatorApplication implements CommandLineRunner {
    @Autowired
    DataSaver dataSaver;

    public static void main(String[] args) {
        SpringApplication.run(AggregatorApplication.class, args);
    }


    @Override
    public void run(String... args) {
        //run commands
        dataSaver.scanAndSaveArticles();
    }


}
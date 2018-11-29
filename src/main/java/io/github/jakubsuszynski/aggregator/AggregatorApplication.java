package io.github.jakubsuszynski.aggregator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AggregatorApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(AggregatorApplication.class, args);
    }


    @Override
    public void run(String... args) {
        //run commands
    }


}
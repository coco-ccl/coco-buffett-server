package org.example.cocobuffettserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CocoBuffettServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CocoBuffettServerApplication.class, args);
    }
}
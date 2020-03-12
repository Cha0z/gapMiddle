package com.d2.concurrency;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class GapApplication {
    public static void main(String[] args) {
        SpringApplication.run(GapApplication.class, args);
    }
}

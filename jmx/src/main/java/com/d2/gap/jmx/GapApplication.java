package com.d2.gap.jmx;

import com.d2.gap.jmx.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * Application was developed with spring boot, because the difference in general in configuration and starting
 * first version of jmx module was written without spring, but i wanted to try more than i`ve done in the first time
 */

@SpringBootApplication
@EnableScheduling
public class GapApplication {
    public static void main(String[] args) {
        SpringApplication.run(GapApplication.class, args);
    }

    @Autowired
    private WorkerService worker;

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        worker.startWork(5);
        System.out.println("hello world, I have just started up");
    }
}
package com.d2.gap.jmx.service;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class Worker extends Thread {
    private final AtomicBoolean running = new AtomicBoolean(false);

    public void stopThread() {
        running.set(false);
        Thread.currentThread().interrupt();
    }

    @Override
    public void run() {
        running.set(true);
        while (running.get()) {
            try {
                if (getRandomNumber() > 20000) {
                    log.info("I`m in the loop");
                    doSomeWork();
                }
                log.info("Thread will stop right now");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.info("Thread was interrupted, Failed to complete operation");
            }
        }
        log.info("We stopped doing something due to the reason someone stop this thread ");
    }

    /**
     * Work that simulating loop state
     */
    private void doSomeWork() {
        int i = 0;
        while (i < 50000) {
            i++;
        }
    }

    private int getRandomNumber() {
        return new Random().nextInt(30000) + 1;
    }


}

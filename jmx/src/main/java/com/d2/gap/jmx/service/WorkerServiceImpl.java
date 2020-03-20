package com.d2.gap.jmx.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@Component
@Slf4j
public class WorkerServiceImpl implements WorkerService {

    private ExecutorService executorService;

    @Override
    public void startWork(Integer threadNumber) {
        executorService = Executors.newFixedThreadPool(threadNumber);
        IntStream.rangeClosed(1, threadNumber)
                .forEach(number -> executorService.submit(new MonitorRunnable(new Worker())));
    }

    @Override
    public void stopThread(String name) {
        synchronized (MonitorRunnable.activeTasks) {
            MonitorRunnable.activeTasks.forEach(r -> {
                if (checkIfThreadHasName(name, (Thread) r)) {
                    transformToWorker(r).stopThread();
                }
            });
        }
    }


    private Worker transformToWorker(Runnable thread) {
        return (Worker) thread;
    }

    private boolean checkIfThreadHasName(String name, Thread thread) {
        return thread.getName().toLowerCase().contains(name.toLowerCase());
    }

    static class MonitorRunnable implements Runnable {

        static final List<Runnable> activeTasks = Collections.synchronizedList(new ArrayList<>());

        private final Runnable runnable;

        public MonitorRunnable(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public void run() {
            activeTasks.add(runnable);
            runnable.run();
            activeTasks.remove(runnable);
        }
    }
}



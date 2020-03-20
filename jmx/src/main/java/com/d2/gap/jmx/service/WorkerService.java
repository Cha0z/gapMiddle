package com.d2.gap.jmx.service;

public interface WorkerService {

    void startWork(Integer threadNumber);

    void stopThread(String name);

}

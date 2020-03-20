package com.d2.gap.jmx.mbean;

import com.d2.gap.jmx.service.WorkerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@ManagedResource
@Component
public class SystemManager implements SystemManagerMBean {

    @Autowired
    private WorkerServiceImpl workerService;

    @Override
    @ManagedOperation
    public void stopThread(String name) {
        workerService.stopThread(name);
    }




}

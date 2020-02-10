package com.d2.gap.jmx.mbean;

public class SystemConfig implements SystemConfigMBean {

    private int threadCount;
    private String schemaName;

    public SystemConfig(int numThreads, String schema) {
        this.threadCount = numThreads;
        this.schemaName = schema;
    }


    @Override
    public int getThreadCount() {
        return threadCount;
    }

    @Override
    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    @Override
    public String getSchemaName() {
        return schemaName;
    }

    @Override
    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    @Override
    public String doConfig() {
        System.out.println("No of Threads=" + this.threadCount + " and DB Schema Name=" + this.schemaName);
        return "No of Threads=" + this.threadCount + " and DB Schema Name=" + this.schemaName;
    }
}

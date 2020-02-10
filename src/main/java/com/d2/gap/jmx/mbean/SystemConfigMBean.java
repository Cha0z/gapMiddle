package com.d2.gap.jmx.mbean;

public interface SystemConfigMBean {
    void setThreadCount(int noOfThreads);

    int getThreadCount();

    void setSchemaName(String schemaName);

    String getSchemaName();

    String doConfig();
}

package com.d2.concurrency.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TestOrder implements Comparable<TestOrder> {

    private String name;
    private BigDecimal money;
    @JsonIgnore
    private LocalDateTime timeOfCreation;

    @Override
    public int compareTo(TestOrder o) {
        return o.timeOfCreation.compareTo(this.timeOfCreation);
    }
}

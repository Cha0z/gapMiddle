package com.d2.concurrency.model;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class TestOrder {

    private String name;
    private BigDecimal money;

}

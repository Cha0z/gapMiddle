package com.d2.concurrency.web.controller;


import com.d2.concurrency.data.OrderDataBaseStub;
import com.d2.concurrency.model.TestOrder;
import com.d2.concurrency.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDataBaseStub db;


    @PostMapping

    private void catchOrder(@RequestBody TestOrder order) {
        setCurrentTime(order);
        db.addOrder(order);
//        orderService.sendOrder(order);
    }

    private void setCurrentTime(@RequestBody TestOrder order) {
//        order.setTimeOfCreation(LocalDateTime.now().minus((int) (Math.random() * 100 + 1), ChronoUnit.SECONDS));
        order.setTimeOfCreation(LocalDateTime.now());
    }

}

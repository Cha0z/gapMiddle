package com.d2.concurrency.web.controller;


import com.d2.concurrency.model.TestOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Slf4j
public class OrderController {


    @PostMapping
    private void catchOrder(@RequestBody TestOrder order){
        System.out.println(order);

    }

}

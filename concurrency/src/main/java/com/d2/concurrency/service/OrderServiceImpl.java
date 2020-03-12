package com.d2.concurrency.service;

import com.d2.concurrency.data.OrderDataBaseStub;
import com.d2.concurrency.model.TestOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderSenderService orderSenderService;
    @Autowired
    private OrderDataBaseStub db;


    public void sendOrder(TestOrder order) {
        orderSenderService.addOrderToQueue(order);
    }

    @Scheduled(fixedRate = 2000)
    public void sendOrderFromDataBase() {
        Optional<TestOrder> order = db.getFirstOrder();
        order.ifPresent((testOrder) -> {
            orderSenderService.addOrderToQueue(testOrder);

        });

    }


}

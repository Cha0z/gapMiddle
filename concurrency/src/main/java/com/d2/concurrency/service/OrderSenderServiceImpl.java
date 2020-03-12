package com.d2.concurrency.service;

import com.d2.concurrency.data.OrderDataBaseStub;
import com.d2.concurrency.model.TestOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;


@Slf4j
@Component
public class OrderSenderServiceImpl implements OrderSenderService {


    private PriorityBlockingQueue<TestOrder> queue = new PriorityBlockingQueue<>();
    @Autowired
    private OrderDataBaseStub db;


    @PostConstruct
    private void initOrderSenderThread() {
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(() -> {
            while (true) {
                workWithOrder();
            }
        });
    }

    private void workWithOrder() throws InterruptedException {
        TestOrder order = getOrderFromDb();
        if (!trySendOrder(order)) {
            db.addOrder(order);
        }
    }

    private TestOrder getOrderFromDb() {
        TestOrder order = null;
        try {
            order = queue.take();
            log.info("Order to sent: {}", order.toString());
            queue.remove(order);
            log.info("Number of orders in queue after try: {}", queue.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return order;
    }

    @Async
    private boolean trySendOrder(TestOrder order) throws InterruptedException {
        if (Math.random() > 0.3) {
            log.info("sending order to another service. Order:{}", order);
            Thread.sleep(2000);
            return true;
        } else {
            log.info("Order can`t be sent ");
            return false;
        }
    }


    @Override
    public void addOrderToQueue(TestOrder testOrder) {
        queue.add(testOrder);
        log.info("Order was added to queue. Number of orders {}", queue.size());
    }


}

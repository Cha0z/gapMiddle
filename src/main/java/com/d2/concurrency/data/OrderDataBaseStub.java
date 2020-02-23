package com.d2.concurrency.data;


import com.d2.concurrency.model.TestOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
@Slf4j
public class OrderDataBaseStub {


    private List<TestOrder> orderList = Collections.synchronizedList(new ArrayList<>());

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();


    public void addOrder(TestOrder order) {

        log.info("List locked");
        readWriteLock.writeLock().lock();
        orderList.add(order);
        readWriteLock.writeLock().unlock();
        log.info("List unlocked");
        log.info("Order was added to db: {}", order);
    }

    public Optional<TestOrder> getFirstOrder() {
        if (checkIfDbHaveOrders()) {
            readWriteLock.readLock().lock();
            log.info("Start reading");
            TestOrder order = orderList.get(0);
            readWriteLock.readLock().unlock();
            deleteOrder(0);
            return Optional.ofNullable(order);
        } else {
            return Optional.empty();
        }

    }

    private boolean checkIfDbHaveOrders() {
        return orderList.size() != 0;
    }

    public boolean deleteOrder(int index) {
        try {
            orderList.remove(index);
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }


}

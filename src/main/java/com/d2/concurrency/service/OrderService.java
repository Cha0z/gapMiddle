package com.d2.concurrency.service;

import com.d2.concurrency.model.TestOrder;

public interface OrderService {

    void sendOrder(TestOrder order);
}

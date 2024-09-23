package com.order_service.controller;

import dto.Order;
import dto.OrderEvent;
import com.order_service.kafka_service.OrderProducer;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }
    @PostMapping("/orders")
    public String placeOrder(@RequestBody Order order) {
        order.setOrderId(UUID.randomUUID().toString());

        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setStatus("PENDING");
        orderEvent.setMessage("order status is in pending state.");
        orderEvent.setOrder(order);

        orderProducer.sendMessage(orderEvent);
        return "Order places successfully .... ";
    }
}

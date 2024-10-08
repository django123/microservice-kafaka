package io.django.orderservice.controller;


import io.django.basedomains.dto.OrderDTO;
import io.django.basedomains.dto.OrderEvent;
import io.django.orderservice.kafka.OrderProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping("/orders")
    public String placeHolder(@RequestBody OrderDTO orderDTO){
        orderDTO.setOrderID(UUID.randomUUID().toString());
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setStatus("PENDING");
        orderEvent.setMessage("Order status is in pending state");
        orderEvent.setOrder(orderDTO);
        orderProducer.sendMessage(orderEvent);
        return "Order placed successfully";
    }
}

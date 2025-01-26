package com.mgnt.warehouse.controller;

import com.mgnt.warehouse.modal.request.CreateOrderRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("order")
public class OrderController {

    @PostMapping("create")
    public void createAnOrder(@RequestBody CreateOrderRequest orderRequest) {

    }
}

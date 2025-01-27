package com.mgnt.warehouse.controller;

import com.mgnt.warehouse.modal.request.CreateOrderRequest;
import com.mgnt.warehouse.modal.response.SuccessResponse;
import com.mgnt.warehouse.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("create")
    public ResponseEntity<SuccessResponse> createAnOrder(@RequestBody @Valid CreateOrderRequest orderRequest) {
        var orderId = orderService.createOrder(orderRequest);
        return ResponseEntity.ok(SuccessResponse.builder().data("order id: " + orderId).build());
    }

    @GetMapping("{id}")
    public ResponseEntity<SuccessResponse> getOrder(@PathVariable("id") String orderId) {
        return ResponseEntity.ok(SuccessResponse.builder()
                .data(orderService.getOrderById(orderId))
                .build());
    }
}

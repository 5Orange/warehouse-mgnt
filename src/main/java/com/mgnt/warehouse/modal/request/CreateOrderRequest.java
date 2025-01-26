package com.mgnt.warehouse.modal.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {
    private String name;
    private String phone;
    private String deliveryAddress;
    private List<OrderItemRequest> orderItems;
}

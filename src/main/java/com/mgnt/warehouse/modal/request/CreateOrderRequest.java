package com.mgnt.warehouse.modal.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {
    private String customerName;
    private String phone;
    private String deliveryAddress;
    @Valid
    @Size(min = 1, message = "items can not be null.")
    private List<OrderItemRequest> orderItems;
}

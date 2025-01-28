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
    private Long discount;
    @Valid
    @Size(min = 1, message = "Product can not be null.")
    private List<OrderProduct> orderProducts;
}

package com.mgnt.warehouse.modal.request;

import lombok.Data;

@Data
public class OrderItemRequest {
    private String productId;
    private Long quantity;
}

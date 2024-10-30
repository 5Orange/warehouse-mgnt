package com.mgnt.warehouse.modal.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductRequest {
    private String name;
    private BigDecimal price;
    private long quantity;
    private String categoryId;
    private String supplierId;
}

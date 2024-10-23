package com.mgnt.warehouse.modal.request;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class CreateProductRequest {
    private String name;
    private BigDecimal price;
    private long quantity;
    private String categoryId;
    private String supplierId;
}

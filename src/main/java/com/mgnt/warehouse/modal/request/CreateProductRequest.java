package com.mgnt.warehouse.modal.request;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CreateProductRequest {
    private String name;
    private BigDecimal price;
    private long quantity;
    private Long categoryId;
    private Long supplierId;
}

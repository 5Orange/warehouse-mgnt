package com.mgnt.warehouse.modal.request;

import java.math.BigDecimal;

public record CreateProductRequest(String name, BigDecimal price,
                                   long quantity, String categoryId,
                                   String supplierId, String description) {
}

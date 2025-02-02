package com.mgnt.warehouse.modal.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record OrderProduct(@NotNull String productId,
                           @Min(0) Long quantity,
                           @DecimalMin("0.0") BigDecimal price) {
}

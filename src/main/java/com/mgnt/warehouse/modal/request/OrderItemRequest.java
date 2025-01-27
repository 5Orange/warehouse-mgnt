package com.mgnt.warehouse.modal.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderItemRequest(@NotNull String productId, @Min(0) Long quantity) {
}

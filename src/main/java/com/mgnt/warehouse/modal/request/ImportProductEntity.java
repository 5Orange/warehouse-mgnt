package com.mgnt.warehouse.modal.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record ImportProductEntity(@NotEmpty String productCode, @NotEmpty String categoryCode,
                                  @NotEmpty String supplierCode, @Min(0) int quantity) {
}

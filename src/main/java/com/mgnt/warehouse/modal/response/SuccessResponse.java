package com.mgnt.warehouse.modal.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SuccessResponse {
    private String message;
    private Object data;
}

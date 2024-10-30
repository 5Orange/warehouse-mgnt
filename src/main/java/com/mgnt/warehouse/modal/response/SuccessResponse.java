package com.mgnt.warehouse.modal.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SuccessResponse {
    @Builder.Default
    private String message = "Success";
    private Object data;

    public static SuccessResponse success(String message, Object data) {
        return SuccessResponse.builder()
            .data(data)
            .message(message).build();
    }

    public static SuccessResponse success(Object data) {
        return SuccessResponse.builder()
            .data(data).build();
    }
}

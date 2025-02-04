package com.mgnt.warehouse.modal.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Builder
@Data
public class ErrorResponse {

    private String message;
    private Object details;
    private HttpStatus status;
}


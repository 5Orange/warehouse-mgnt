package com.mgnt.warehouse.modal.response;

import lombok.Builder;

@Builder
public class LoginResponse {
    private String token;
    @Builder.Default
    private String tokenType = "Bearer";
}

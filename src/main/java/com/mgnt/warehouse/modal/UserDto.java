package com.mgnt.warehouse.modal;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String individualCard;
    private String email;
    private String fullName;
    private String address;
    private String phoneNumber;
    private boolean isActive;
}

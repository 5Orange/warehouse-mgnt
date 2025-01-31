package com.mgnt.warehouse.modal.request;

public record ChangePasswordRequest(String userName, String oldPassword, String newPassword) {
}

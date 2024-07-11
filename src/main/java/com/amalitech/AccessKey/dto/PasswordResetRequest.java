package com.amalitech.AccessKey.dto;

public record PasswordResetRequest(
        String email,
        String code,
        String password
) {
}

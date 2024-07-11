package com.amalitech.AccessKey.dto;

public record VerifyOtpDto(
        String code,
        String email
) {
}

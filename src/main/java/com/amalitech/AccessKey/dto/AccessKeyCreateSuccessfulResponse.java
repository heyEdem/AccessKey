package com.amalitech.AccessKey.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record AccessKeyCreateSuccessfulResponse (
        String message,
        String code,
        LocalDate expiry,
        String status
){
}

package com.amalitech.AccessKey.dto;

import lombok.Builder;

@Builder
public record SignedUpSuccessResponse(
        String name,
        String email,
        String token
) {
}

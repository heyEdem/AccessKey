package com.amalitech.AccessKey.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import static com.amalitech.AccessKey.utils.Validator.*;
public record AccessKeyRequest(
        @NotNull(message = FIELD_IS_REQUIRED)
        @NotBlank(message = FIELD_IS_REQUIRED)
        String name) {
}

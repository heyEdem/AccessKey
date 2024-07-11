package com.amalitech.AccessKey.dto;

import com.amalitech.AccessKey.utils.Validator;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import javax.lang.model.element.Name;

import static com.amalitech.AccessKey.utils.Validator.*;

public record SignupRequestDto(
        @NotBlank(message = NAME_NOT_BLANK)
        @NotNull(message = NAME_NOT_NULL)
        String name,

        @NotBlank(message = NAME_NOT_BLANK)
        @NotNull(message = NAME_NOT_NULL)
        String email,

        @Min(value = PASSWORD_MIN_LENGTH, message = PASSWORD_SIZE)
        @Max(value = PASSWORD_MAX_LENGTH, message = PASSWORD_SIZE)
        @NotNull(message = PASSWORD_NOT_NULL)
        @NotBlank(message = PASSWORD_NOT_BLANK)
        String password

) {
}

package com.amalitech.AccessKey.dto;

import com.amalitech.AccessKey.entities.OtpType;
import lombok.Builder;

@Builder
public record OtpEMailTemplate(String to,
                               String otp,
                               OtpType otpType
) {
}

package com.amalitech.AccessKey.service;


import com.amalitech.AccessKey.entities.OtpType;

public interface OtpService {

     void generateOtp(String email, OtpType type);

     boolean isOtpValid(String otpCode, String email);

     void invalidateOtp(String otpCode);
}

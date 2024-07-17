package com.amalitech.AccessKey.service;

import com.amalitech.AccessKey.dto.*;


public interface AuthService {

    GenericMessageResponse register(SignupRequestDto request);

    SignedUpSuccessResponse verifyOtpAndSignupUser(VerifyOtpDto request);

    NormalUserResponseDto login(LoginRequestDto request);

    GenericMessageResponse resendOtp(ResendOtpRequest request);

    GenericMessageResponse passwordResetRequest(ResetPasswordRequest request);

    GenericMessageResponse resetPasswordWithToken(PasswordResetRequest request);

}

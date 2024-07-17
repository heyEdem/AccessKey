package com.amalitech.AccessKey.controller;

import com.amalitech.AccessKey.dto.*;
import com.amalitech.AccessKey.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "User signup",
            method = "POST"
    )
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public GenericMessageResponse register(@RequestBody SignupRequestDto request) {
        return authService.register(request);
    }

    @Operation(
            summary = "Verify otp and signup user",
            method = "POST"
    )
    @PostMapping("/verify-user")
    @ResponseStatus(HttpStatus.CREATED)
    public SignedUpSuccessResponse verifyOtpAndSignupUser(@RequestBody VerifyOtpDto request) {
        return authService.verifyOtpAndSignupUser(request);
    }

    @Operation(
            summary = "login user",
            method = "POST"
    )
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public NormalUserResponseDto login(@RequestBody LoginRequestDto request) {
        return authService.login(request);
    }

    @Operation(
            summary = "Request new Otp, check schema for otp type",
            method = "POST"
    )
    @PostMapping("/resend-otp")
    @ResponseStatus(HttpStatus.OK)
    public GenericMessageResponse resendOtp(@RequestBody ResendOtpRequest request) {
        return authService.resendOtp(request);
    }

    @Operation(
            summary = "Request otp for password reset token",
            method = "POST"
    )
    @PostMapping("/request-password-reset")
    @ResponseStatus(HttpStatus.OK)
    public GenericMessageResponse getResetPasswordToken(@RequestBody ResetPasswordRequest request) {
        return authService.passwordResetRequest(request);
    }


    @Operation(
            summary = "password reset ",
            method = "POST"
    )
    @PostMapping("/reset-password")
    @ResponseStatus(HttpStatus.OK)
    public GenericMessageResponse resetPassword(@RequestBody PasswordResetRequest request) {
        return authService.resetPasswordWithToken(request);
    }


}

package com.amalitech.AccessKey.service;

import com.amalitech.AccessKey.entities.OtpType;
import com.amalitech.AccessKey.exception.DuplicateEmailException;
import com.amalitech.AccessKey.exception.NotFoundException;
import com.amalitech.AccessKey.exception.VerificationFailedException;
import com.amalitech.AccessKey.repository.UserRepository;
import com.amalitech.AccessKey.security.JwtService;
import com.amalitech.AccessKey.security.SecurityUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.amalitech.AccessKey.dto.*;
import com.amalitech.AccessKey.entities.User;
import com.amalitech.AccessKey.entities.Roles;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final OtpService otpService;

    private static final String TOKEN_SENT_MSG = "Token Sent, check your email";
    private static final String NOT_VERIFY_OTP = "Could not verify Otp, Invalid or Expired Otp";
    private static final String DUPLICATE_EMAIL_MSG = "This Email is already registered";
    private static final String NOT_FOUND_MSG = "Invalid email or Password";
    private static final String RESET_PASSWORD_SUCCESS = "Password Reset Successfully, login in";


    @Override
    public GenericMessageResponse register(SignupRequestDto request) {

        Optional<User> byEmail = repository.findByEmail(request.email());

        if (byEmail.isPresent()) {
            throw new DuplicateEmailException(DUPLICATE_EMAIL_MSG);
        }

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .roles(Roles.SCHOOL)
                .build();

        otpService.generateOtp(user.getEmail(), OtpType.CREATE);
        repository.save(user);

        return new GenericMessageResponse(TOKEN_SENT_MSG);
    }

    @Override
    public SignedUpSuccessResponse verifyOtpAndSignupUser(VerifyOtpDto request) {

        boolean otpValid = otpService.isOtpValid(request.code(), request.email());

        if (!otpValid) {
            throw new VerificationFailedException(NOT_VERIFY_OTP);
        }

        User user = repository.findByEmail(request.email()).orElseThrow(() -> new VerificationFailedException(NOT_VERIFY_OTP));
        user.setEnabled(true);

        repository.save(user);

        otpService.invalidateOtp(request.code());

        String token = jwtService.generateToken(new SecurityUser(user));

        return SignedUpSuccessResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .token(token)
                .build();
    }

    @Override
    public NormalUserResponseDto login(LoginRequestDto request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        User user = repository.findByEmail(request.email()).orElseThrow(() -> new NotFoundException(NOT_FOUND_MSG));

        String token = jwtService.generateToken(new SecurityUser(user));

        return NormalUserResponseDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRoles())
                .token(token)
                .build();
    }

    @Override
    public GenericMessageResponse resendOtp(ResendOtpRequest request) {
        User user = repository.findByEmail(request.email()).orElseThrow(() -> new NotFoundException(NOT_FOUND_MSG));

        otpService.generateOtp(user.getEmail(), OtpType.valueOf(request.type().toUpperCase()));
        return new GenericMessageResponse(TOKEN_SENT_MSG);
    }

    @Override
    public GenericMessageResponse passwordResetRequest(ResetPasswordRequest request) {
        Optional<User> byEmail = repository.findByEmail(request.email());
        if (byEmail.isEmpty()) throw new NotFoundException(NOT_FOUND_MSG);

        otpService.generateOtp(request.email(), OtpType.RESET);
        return new GenericMessageResponse(TOKEN_SENT_MSG);
    }

    @Override
    public GenericMessageResponse resetPasswordWithToken(PasswordResetRequest request) {
        boolean otpValid = otpService.isOtpValid(request.code(), request.email());

        if (!otpValid) throw new VerificationFailedException(NOT_VERIFY_OTP);

        User user = repository.findByEmail(request.email()).orElseThrow(() -> new NotFoundException(NOT_FOUND_MSG));

        user.setPassword(passwordEncoder.encode(request.password()));
        repository.save(user);

        otpService.invalidateOtp(request.code());

        return new GenericMessageResponse(RESET_PASSWORD_SUCCESS);
    }
}

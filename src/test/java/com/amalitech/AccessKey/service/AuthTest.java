package com.amalitech.AccessKey.service;

import com.amalitech.AccessKey.dto.*;
import com.amalitech.AccessKey.entities.OtpType;
import com.amalitech.AccessKey.entities.Roles;
import com.amalitech.AccessKey.entities.User;
import com.amalitech.AccessKey.repository.UserRepository;
import com.amalitech.AccessKey.security.JwtServiceImpl;
import com.amalitech.AccessKey.security.SecurityUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class AuthTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private OtpServiceImpl otpService;

    @Mock
    private JwtServiceImpl jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthServiceImpl authService;

    private User user;

    private String email;

    private String password;

    @BeforeEach
    void setUp() {
        email = "test@user.com";
        password = "encoded_password";
        user = User.builder()
                .name("test")
                .email(email)
                .password(password)
                .roles(Roles.SCHOOL)
                .build();
    }

    @Test
    void should_register_user() {

        SignupRequestDto requestDto = SignupRequestDto.builder()
                .name("test")
                .email(email)
                .password(password)
                .build();
        GenericMessageResponse expectedResponse = new GenericMessageResponse("Token Sent, check your email");
        when(userRepository.findByEmail(requestDto.email())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(requestDto.password())).thenReturn(password);

        // When
        GenericMessageResponse actualResponse = authService.register(requestDto);

        // Then
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(userRepository, times(1)).findByEmail(requestDto.email());
        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode(requestDto.password());
        verify(otpService, times(1)).generateOtp(requestDto.email(), OtpType.CREATE);
    }


    @Test
    void verifyOtpAndSignupUserTest() {

        //Give
        String code = "123456";
        VerifyOtpDto verifyOtpDto = new VerifyOtpDto(code, email);

        SignedUpSuccessResponse response = SignedUpSuccessResponse.builder()
                .token(UUID.randomUUID().toString())
                .email(email)
                .name("test")
                .build();


        when(otpService.isOtpValid(code, email)).thenReturn(true);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any(SecurityUser.class))).thenReturn("jwtToken");


        SignedUpSuccessResponse request = authService.verifyOtpAndSignupUser(verifyOtpDto);
        assertNotNull(request);
        assertEquals(response.email(), request.email());
        assertEquals(response.name(), request.name());

        verify(otpService, times(1)).isOtpValid(code, email);
        verify(userRepository, times(1)).findByEmail(email);
        verify(jwtService, times(1)).generateToken(any(SecurityUser.class));

    }

    @Test
    void loginTest() {
        //given

        LoginRequestDto requestDto = new LoginRequestDto(email, password);

        NormalUserResponseDto responseDto = NormalUserResponseDto.builder()
                .name(user.getName())
                .email(email)
                .token("jwt")
                .build();

        //when
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mock(Authentication.class));
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any(SecurityUser.class))).thenReturn("jwt");
        NormalUserResponseDto response = authService.login(requestDto);

        //then
        assertNotNull(response);
        assertEquals(responseDto.email(), response.email());
        assertEquals(responseDto.name(), response.name());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findByEmail(email);
        verify(jwtService, times(1)).generateToken(any(SecurityUser.class));

    }
}

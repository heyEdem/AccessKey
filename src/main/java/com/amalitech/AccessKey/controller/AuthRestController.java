package com.amalitech.AccessKey.controller;


import com.amalitech.AccessKey.service.AccessKeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

import com.amalitech.AccessKey.dto.*;
import com.amalitech.AccessKey.service.AuthService;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthService authService;
    private final AccessKeyService accessKeyService;

    @GetMapping("/auth/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/auth/signup")
    public String showSignupPage() {
        return "signup";
    }

    @GetMapping("/auth/forgot-password")
    public String showForgotPasswordPage() {
        return "forgotpassword";
    }

    @GetMapping("/home")
    public String showHomePage(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               Principal principal,
                               Model model) {
        Page<GetAccessKeyProjection> accessKeysPage = accessKeyService.getAllUserKeys( page, size, principal);
        model.addAttribute("accessKeysPage", accessKeysPage);
        return "home";
    }





    @PostMapping("/auth/login")
    public String login(@Valid @ModelAttribute LoginRequestDto loginRequest, Model model) {
            authService.login(loginRequest);
        return "redirect:/home";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute SignupRequestDto signupRequest, Model model) {
        try {
            GenericMessageResponse response = authService.register(signupRequest);
            // Assuming you redirect to a success page or login page after signup
            return "redirect:/auth/login";
        } catch (Exception e) {
            model.addAttribute("error", "Signup failed");
            return "signup";
        }
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@Valid @ModelAttribute ResetPasswordRequest resetRequest, Model model) {
        try {
            GenericMessageResponse response = authService.passwordResetRequest(resetRequest);
            // Assuming you redirect to a page confirming the request was successful
            return "redirect:/auth/login";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to send password reset request.");
            return "forgotpassword";
        }
    }



}

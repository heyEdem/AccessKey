package com.amalitech.AccessKey.service;

import com.amalitech.AccessKey.dto.OtpEMailTemplate;
import com.amalitech.AccessKey.entities.Otp;
import com.amalitech.AccessKey.entities.OtpType;
import com.amalitech.AccessKey.exception.VerificationFailedException;
import com.amalitech.AccessKey.repository.OtpRepository;
import com.amalitech.AccessKey.utils.OtpEmailSender;
import lombok.AllArgsConstructor;


import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.amalitech.AccessKey.utils.Validator.VERIFICATION_FAILED_MESSAGE;

@Service
@AllArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final OtpRepository repository;
    private final OtpEmailSender otpEmailSender;
    private static final int OTP_LENGTH = 6;


    @Override
    public void generateOtp(String email, OtpType type) {
        String code = generator();
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(5);

        Otp otp = Otp.builder()
                .code(code)
                .type(type)
                .expiredAt(expirationTime)
                .email(email)
                .build();
        OtpEMailTemplate mailTemplate = getOtpEMailTemplate(email, code, type);
        otpEmailSender.send(mailTemplate);

        repository.save(otp);

    }

    private static OtpEMailTemplate getOtpEMailTemplate(String email, String code, OtpType type) {
        return OtpEMailTemplate.builder()
                .to(email)
                .otpType(type)
                .otp(code)
                .build();
    }


    @Override
    public boolean isOtpValid(String otpCode, String email) {
        Otp otp = repository.findByCode(otpCode).orElseThrow(() -> new VerificationFailedException(VERIFICATION_FAILED_MESSAGE));

        if (!Objects.equals(otp.getEmail(), email)) throw new VerificationFailedException(VERIFICATION_FAILED_MESSAGE);

        boolean isOtpExpired = isOtpExpired(otp);

        return !isOtpExpired;
    }

    @Override
    public void invalidateOtp(String otpCode) {
        Otp otp = repository.findByCode(otpCode).orElseThrow(() -> new VerificationFailedException(VERIFICATION_FAILED_MESSAGE));
        otp.setExpiredAt(LocalDateTime.now());
        repository.save(otp);
    }

    private boolean isOtpExpired(Otp otp) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expiredAt = otp.getExpiredAt();

        return currentTime.isAfter(expiredAt);
    }


    private String generator() {
        SecureRandom random = new SecureRandom();

        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }

        return otp.toString();
    }
}

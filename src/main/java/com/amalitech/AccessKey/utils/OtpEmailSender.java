package com.amalitech.AccessKey.utils;

import com.amalitech.AccessKey.dto.OtpEMailTemplate;
import com.amalitech.AccessKey.entities.OtpType;
import com.amalitech.AccessKey.exception.VerificationFailedException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import static com.amalitech.AccessKey.utils.Validator.*;


@Component
@RequiredArgsConstructor
public class OtpEmailSender implements EmailService<OtpEMailTemplate> {

    private final JavaMailSender javaMailSender;
    private final ITemplateEngine templateEngine;



    @Override
    public void send(OtpEMailTemplate message) {
        sendOtpMail(message.to(), message.otp(), message.otpType());
    }

    private void sendOtpMail(String to, String otp, OtpType otpType) {

        try {
            if (!isValidEmail(to)) throw new EntityNotFoundException(NOT_FOUND_MSG);

            Context context = new Context();
            context.setVariable("otp", otp);

            String templateType = otpType == OtpType.CREATE ? "email-otp-template" : "email-reset-template";
            String process = templateEngine.process(templateType, context);
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setSubject("OTP password for Amalitech AccessKeys");
            helper.setText(process, true);
            helper.setTo(to);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new VerificationFailedException(VERIFICATION_FAILED);
        }
    }
}

package org.com.session06.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.session06.helper.OTPGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine springTemplateEngine;

    @Value("${spring.mail.from}")
    private String mailFrom;

    public void sendRepass(String recipient, String newPass) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED, StandardCharsets.UTF_8.name());

        Context context = new Context();
        String newPassword = newPass;

        Map<String, Object> properties = new HashMap<>();
        properties.put("newPassword", newPassword);
        context.setVariables(properties);
        helper.setFrom(mailFrom, "Admin");
        helper.setTo(recipient);
        helper.setSubject("Your reset password.");
        String html = springTemplateEngine.process("forgotpw-mail.html", context);
        helper.setText(html,true);
        mailSender.send(message);

        log.info("Email sent");
    }


    public void sendVerifyCode(String recipient, String otp) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED, StandardCharsets.UTF_8.name());

        Context context = new Context();
        String otpCode = otp;

        Map<String, Object> properties = new HashMap<>();
        properties.put("otpCode", otpCode);
        context.setVariables(properties);
        helper.setFrom(mailFrom, "Admin");
        helper.setTo(recipient);
        helper.setSubject("Your OTP Code.");
        String html = springTemplateEngine.process("otp-mail.html", context);
        helper.setText(html,true);
        mailSender.send(message);

        log.info("Email sent");
    }
}

package dochigosum.simvex.global.mail.service;

import dochigosum.simvex.global.error.GlobalErrorCode;
import dochigosum.simvex.global.error.exception.SimvexException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class MailService {

    private static final SecureRandom RANDOM = new SecureRandom();

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public String generateCode() {
        StringBuilder code = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            code.append(RANDOM.nextInt(10));
        }
        return code.toString();
    }

    public void sendVerificationEmail(String toEmail, String code) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, toEmail);
            message.setSubject("이메일 인증");

            String body = "";
            body += "<h3>요청하신 인증 번호입니다.</h3>";
            body += "<h1>" + code + "</h1>";
            body += "<h3>감사합니다.</h3>";
            message.setText(body, "UTF-8", "html");

            javaMailSender.send(message);
        } catch (MessagingException | MailException e) {
            throw new SimvexException(GlobalErrorCode.INTERNAL_SERVER_ERROR, "이메일 전송에 실패했습니다.");
        }
    }
}

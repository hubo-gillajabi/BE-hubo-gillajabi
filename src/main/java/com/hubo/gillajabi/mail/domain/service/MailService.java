package com.hubo.gillajabi.mail.domain.service;

import com.hubo.gillajabi.agreement.domain.entity.MemberAgreement;
import com.hubo.gillajabi.mail.infrastructure.config.MailConfig;
import com.hubo.gillajabi.mail.infrastructure.exception.MailException;
import com.hubo.gillajabi.mail.infrastructure.exception.MailExceptionCode;
import com.hubo.gillajabi.member.domain.entity.Member;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final MailConfig mailConfig;

    private final static String MAIL_SUBJECT = "길라잡이 위치정보 이용약관 안내";

    public CompletableFuture<Void> sendAgreementMail(Member member, List<MemberAgreement> memberAgreements) {
        return CompletableFuture.runAsync(() -> {
            try {
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

                helper.setFrom(mailConfig.getUsername());
                helper.setTo(member.getEmail());
                helper.setSubject(MAIL_SUBJECT);

                Context context = new Context();
                context.setVariable("name", member.getNickName());

                LocalDateTime agreedAt = memberAgreements.isEmpty()
                        ? LocalDateTime.now()
                        : memberAgreements.get(0).getModifiedTime();

                context.setVariable("agreedAt", agreedAt);

                String htmlContent = templateEngine.process("email/agreement", context);
                helper.setText(htmlContent, true);
                javaMailSender.send(message);

            } catch (MessagingException e) {
                log.error("약관 메일 전송 중 오류 발생", e);
                throw new MailException(MailExceptionCode.MAIL_FAILED_TO_SEND);
            }
        });
    }
}


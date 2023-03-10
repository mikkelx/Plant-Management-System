package com.example.pms.sign;

import com.example.pms.dto.NotificationEmail;
import com.example.pms.exceptions.ActivationException;
import com.example.pms.userLog.TechnicalLogs;
import com.example.pms.userLog.TechnicalLogsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;
    private final TechnicalLogsRepository technicalLogsRepository;

    @Async
    public void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("BloomBoost@mail.com");
            messageHelper.setTo(notificationEmail.getRecipent());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Email sent!!");
            technicalLogsRepository.save(new TechnicalLogs("Email sent to: " + notificationEmail.getRecipent()));
        } catch (MailException e) {
            throw new ActivationException("Exception occurred when sending mail to " + notificationEmail.getRecipent()+ e.getMessage());
        }
    }


}
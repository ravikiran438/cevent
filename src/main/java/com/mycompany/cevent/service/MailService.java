package com.mycompany.cevent.service;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Service for sending e-mails.
 * <p/>
 * <p>
 * We use the @Async annotation to send e-mails asynchronously.
 * </p>
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    @Inject
    private JavaMailSenderImpl javaMailSender;
    
    @Inject
    private Environment env;

    @Async
    public void sendEmail(String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        String to = env.getProperty("mail.to");
        String from = env.getProperty("mail.from");
        String subject = env.getProperty("mail.subject");
        message.setTo(to);
        message.setFrom(from);
        message.setSubject(subject);
        message.setText(text);
        try {
            javaMailSender.send(message);
            log.debug("Sent e-mail to User '{}'!", to);
        } catch (MailException me) {
            log.warn("E-mail could not be sent to user '{}', exception is: {}", to, me.getMessage());
        }
    }
}

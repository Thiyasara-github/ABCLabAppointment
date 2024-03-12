package com.ABCLab.ABCLab.email;

import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService {

    private final JavaMailSender mailSender;

    public SendEmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @EventListener
    public void handleSendEmailEvent(SendEmailEvent event) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("vithanagettp@gmail.com");
        message.setTo(event.getToEmail());
        message.setSubject(event.getSubject());
        message.setText(event.getBody());

        mailSender.send(message);

        System.out.println("Mail sent successfully to " + event.getToEmail());
    }
}

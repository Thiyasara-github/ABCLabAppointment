package com.ABCLab.ABCLab.email;

import org.springframework.context.ApplicationEvent;

public class SendEmailEvent extends ApplicationEvent {
    private final String toEmail;
    private final String subject;
    private final String body;

    public SendEmailEvent(Object source, String toEmail, String subject, String body) {
        super(source);
        this.toEmail = toEmail;
        this.subject = subject;
        this.body = body;
    }

    public String getToEmail() {
        return toEmail;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
}

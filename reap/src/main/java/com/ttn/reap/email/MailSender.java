package com.ttn.reap.email;

import com.ttn.reap.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class MailSender {

    @Autowired
    EmailService emailService;

    public void sendMail(String receiver,String subject,String text){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("arpitg662@gmail.com");
        mail.setTo(receiver);
        mail.setSubject(subject);
        mail.setText(text);
        emailService.sendEmail(mail);
    }
}

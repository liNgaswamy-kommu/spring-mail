package com.eidiko.spring_mail.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmailWithAttachments(String to,
                                         String subject,
                                         String body,
                                         MultipartFile[] files)
            throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body,true);

        if(files != null){
            for(MultipartFile file : files){
                helper.addAttachment(file.getOriginalFilename(), file);
            }
        }

        mailSender.send(message);
    }

    public void sendMailWithPdf(String to,String subject,
                                String body,byte[] pdfBytes)
            throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);

        helper.addAttachment("employee-report.pdf",
                new ByteArrayResource(pdfBytes));

        mailSender.send(message);
    }
}

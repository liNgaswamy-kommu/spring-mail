package com.eidiko.spring_mail.controller;

import com.eidiko.spring_mail.dto.EmailDTO;
import com.eidiko.spring_mail.service.EmailService;
import com.sun.source.tree.BreakTree;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailDTO request) throws MessagingException {
        String body = """
        Hello User
        Welcome to our application.

        Regards,
        HR Team
        """;
        emailService.
                sendEmail(
                        request.getToEmail(),
                        request.getSubject(),
                        body
                );
        return "Mail sent Successfully";
    }
}


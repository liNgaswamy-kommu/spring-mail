package com.eidiko.spring_mail.controller;

import com.eidiko.spring_mail.dto.EmailRequest;
import com.eidiko.spring_mail.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping(value = "/send",
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String sendEmail(@ModelAttribute EmailRequest request) throws MessagingException {
        String body = """
        Hello User
        Welcome to our application.

        Regards,
        HR Team
        """;
        emailService.sendEmailWithAttachments(
                        request.getToEmail(),
                        request.getSubject(),
                        body,
                        request.getFiles()
                );
        return "Mail sent Successfully";
    }
}


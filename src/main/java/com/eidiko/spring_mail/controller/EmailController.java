package com.eidiko.spring_mail.controller;

import com.eidiko.spring_mail.service.EmailService;
import com.sun.source.tree.BreakTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public String sendEmail() {
        emailService.
                sendEmail("lingaswamykommu95@gmail.com",
                        "Dummy Mail",
                        "Welcome to Spring mail");
        return "Mail sent Successfully";
    }
}

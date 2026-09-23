package com.eidiko.spring_mail.controller;

import com.eidiko.spring_mail.dto.EmailReportRequest;
import com.eidiko.spring_mail.dto.EmailRequest;
import com.eidiko.spring_mail.dto.EmailResponse;
import com.eidiko.spring_mail.service.EmailReaderService;
import com.eidiko.spring_mail.service.EmailService;
import com.eidiko.spring_mail.service.PdfService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private EmailReaderService readerService;

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

    @PostMapping("/send-report")
    public String generateSendReport(@RequestBody EmailReportRequest request)
            throws MessagingException {

        String[] array = request.getToEmail().split("@");
        byte[] pdfBytes = pdfService.generatePdf(array[0],request.getToEmail());

        emailService.sendMailWithPdf(request.getToEmail(),
                request.getSubject(),
                request.getBody(),
                pdfBytes);
        return "report sent successfully";
    }

    @GetMapping("/read")
    public List<EmailResponse> readEmails(){
        return readerService.readEmails();
    }
}


package com.eidiko.spring_mail.dto;

import lombok.Data;

@Data
public class EmailReportRequest {
    private String toEmail;
    private String subject;
    private String body;
}

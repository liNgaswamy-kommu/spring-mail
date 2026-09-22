package com.eidiko.spring_mail.dto;

import lombok.Data;

@Data
public class EmailDTO {
    private String toEmail;
    private String subject;
    private String body;
}

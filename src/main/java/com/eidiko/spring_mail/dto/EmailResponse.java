package com.eidiko.spring_mail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailResponse {
    private String from;
    private String subject;
    private String body;
    private Date receivedDate;
}

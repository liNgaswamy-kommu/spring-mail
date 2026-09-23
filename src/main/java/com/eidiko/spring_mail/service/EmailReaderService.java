package com.eidiko.spring_mail.service;

import com.eidiko.spring_mail.dto.EmailResponse;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class EmailReaderService {

    @Value("${mail.imap.host}")
    private String host;

    @Value("${mail.imap.port}")
    private String port;

    @Value("${mail.imap.username}")
    private String username;

    @Value("${mail.imap.password}")
    private String password;


    public List<EmailResponse> readEmails(){
        List<EmailResponse> emails = new ArrayList<>();
        try{
            //1. IMAP Properties
            Properties properties = new Properties();

            properties.put("mail.store.protocol","imaps");
            properties.put("mail.imaps.host",host);
            properties.put("mail.imaps.port",port);
            properties.put("mail.imaps.ssl.enable","true");

            //2. Create mail Session
            Session session = Session.getInstance(properties);

            //3. Connect to mail server
            Store store = session.getStore("imaps");
            store.connect(host, username, password);

            //4. Open Inbox
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            //5. Get messages
            Message[] messages = inbox.getMessages();
            System.err.println("Total messages: " + messages.length);
            //6. Read messages
            for(Message message : messages){
                try {

                    System.err.println("Reading message...");
                    EmailResponse response = new EmailResponse();

                    response.setSubject(message.getSubject());
                    response.setReceivedDate(message.getReceivedDate());

                    Address[] fromAddresses = message.getFrom();

                    if (fromAddresses != null && fromAddresses.length > 0) {
                        InternetAddress address = (InternetAddress) fromAddresses[0];
                        response.setFrom(address.getAddress());
                    }

                    response.setBody(getTextFromMessage(message));
                    emails.add(response);

                } catch (Exception e) {
                    System.out.println("Failed to read one email: " + e.getMessage());
                    e.printStackTrace();
                }

            }
            //7. Close inbox
            inbox.close();

            //8. Close connection
            store.close();

        }catch (Exception e){
            throw new RuntimeException("Unable to read mails"+e.getMessage(),e);
        }

        return emails;
    }

    private String getTextFromMessage(Message message) throws MessagingException, IOException {
        if (message.isMimeType("text/plain")) {
            return message.getContent().toString();
        }

        if (message.isMimeType("text/html")) {
            return message.getContent().toString();
        }

        if (message.isMimeType("multipart/*")) {
            Multipart multipart =
                    (Multipart) message.getContent();

            StringBuilder result = new StringBuilder();

            for (int i = 0; i < multipart.getCount(); i++) {

                BodyPart bodyPart = multipart.getBodyPart(i);

                if (bodyPart.isMimeType("text/plain")) {
                    result.append(bodyPart.getContent().toString());
                }
            }
            return result.toString();
        }
        return "";
    }

}


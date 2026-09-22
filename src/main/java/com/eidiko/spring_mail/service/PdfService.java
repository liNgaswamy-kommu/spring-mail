package com.eidiko.spring_mail.service;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    public byte[] generatePdf(String name,String email){

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();

        try {
            PdfWriter.getInstance(document,outputStream);

            document.open();
            document.add(new Paragraph("Employee Report"));
            document.add(new Paragraph("Employee Name : "+name));
            document.add(new Paragraph("Employee email : "+email));
            document.close();
            return outputStream.toByteArray();
        }catch (Exception ex){
            throw new RuntimeException("Failed to generate PDF",ex);
        }
    }
}


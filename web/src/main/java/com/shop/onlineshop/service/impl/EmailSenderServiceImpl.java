package com.shop.onlineshop.service.impl;

import com.shop.onlineshop.model.entities.Product;
import com.shop.onlineshop.service.EmailSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EmailSenderServiceImpl implements EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(String toEmail, String subject, String body, List<Product> productList) {
        SimpleMailMessage message = new SimpleMailMessage();

        String text = body;
        for(int i=0; i<productList.size(); i++){
            text = body + "\n" + productList.get(i).getProductName() + "\n";
        }
        message.setFrom("infinity.aitu@gmail.com");
        message.setTo(toEmail);
        message.setText(text);
        message.setSubject(subject);

        mailSender.send(message);

        log.info("In sendEmail message sent");
    }
}

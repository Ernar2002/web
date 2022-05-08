package com.shop.onlineshop.service.impl;

import com.shop.onlineshop.model.entities.Product;
import com.shop.onlineshop.service.EmailSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class EmailSenderServiceImpl implements EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(String toEmail, String subject, String body, List<Product> productList, int totalSum) {
        SimpleMailMessage message = new SimpleMailMessage();

        Date date = new Date();
        StringBuilder text = new StringBuilder(body + "\n" + date + "\n\nPurchased products: \n");
        for(int i=0; i<productList.size(); i++){
            text.append(productList.get(i).getProductName()).append("\n");
        }

        text.append("\n\nTotal sum: " + totalSum + " ₸");
        message.setFrom("infinity.aitu@gmail.com");
        message.setTo(toEmail);
        message.setText(text.toString());
        message.setSubject(subject);

        mailSender.send(message);

        log.info("In sendEmail message sent");
    }
}

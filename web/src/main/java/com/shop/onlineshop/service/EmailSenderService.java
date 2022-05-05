package com.shop.onlineshop.service;

import com.shop.onlineshop.model.entities.Product;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EmailSenderService {
    void sendEmail(String toEmail, String subject, String body, List<Product> productList ) ;
}

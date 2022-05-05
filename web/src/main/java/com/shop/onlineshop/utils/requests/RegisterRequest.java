package com.shop.onlineshop.utils.requests;

import lombok.Data;

@Data
public class RegisterRequest {
    String email, firstName, lastName, password;
}

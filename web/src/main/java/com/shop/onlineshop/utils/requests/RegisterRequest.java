package com.shop.onlineshop.urils.requests;

import lombok.Data;

@Data
public class RegisterRequest {
    String email, firstName, lastName, password;
}

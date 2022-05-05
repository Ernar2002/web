package com.shop.onlineshop.utils.responses;

import com.shop.onlineshop.model.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class LoginResponse {
    private boolean error;
    private String message;
    private User user;
}

package com.shop.onlineshop.controller.api;

import com.shop.onlineshop.model.entities.User;
import com.shop.onlineshop.service.UserService;
import com.shop.onlineshop.utils.requests.RegisterRequest;
import com.shop.onlineshop.utils.responses.LoginResponse;
import com.shop.onlineshop.utils.responses.RegisterResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiAuthController {
    private static final Logger logger = LoggerFactory.getLogger(ApiAuthController.class);

    private final UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ApiAuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public LoginResponse login(@RequestParam String email, @RequestParam String password) {
        User findUser = userService.findByEmail(email);

        LoginResponse loginResponse = new LoginResponse();
        if(findUser!=null){

            if(passwordEncoder.matches(password, findUser.getPassword())){
                loginResponse.setUser(findUser);
                loginResponse.setError(false);
                return loginResponse;
            } else {
                loginResponse.setMessage("Invalid password");
                loginResponse.setError(true);
                return loginResponse;
            }

        } else {
            loginResponse.setMessage("User not found");
            loginResponse.setError(true);
            return loginResponse;
        }
    }

    @GetMapping("/signup")
    public RegisterResponse signup(@RequestParam String email, @RequestParam String password, @RequestParam String firstName, @RequestParam String lastName) {
        User newUser = userService.findByEmail(email);
        RegisterResponse response = new RegisterResponse();
        if(newUser==null){
            User user = new User();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setRole("USER");
            user.setActive(1);
            userService.save(user);
            response.setError(false);
            response.setMessage("User created");
        } else {
            response.setError(true);
            response.setMessage("User exists");
        }

        return response;
    }


}

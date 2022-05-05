package com.shop.onlineshop.controller.api;

import com.shop.onlineshop.model.entities.User;
import com.shop.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiProfileController {

    @Autowired
    private UserService userService;

    @PostMapping("/update-profile")
    public User updateProfile(@RequestParam String email, @RequestParam String fName,
                              @RequestParam String lName, @RequestParam String city,
                              @RequestParam String address){
        User userFromDb = userService.findByEmail(email);

        if(userFromDb!=null){
            userFromDb.setFirstName(fName);
            userFromDb.setLastName(lName);
            userFromDb.setCity(city);
            userFromDb.setAddress(address);

            userService.save(userFromDb);

            return userFromDb;
        }

        return null;
    }
}

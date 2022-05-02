package com.shop.onlineshop.controller;

import com.shop.onlineshop.model.GoogleUserInfo;
import com.shop.onlineshop.model.entities.User;
import com.shop.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("/get-information")
    public ModelAndView getProfile(Principal principal,  @AuthenticationPrincipal OAuth2User oauth2User){
        User user = getPrincipalUser(principal, oauth2User);
        ModelAndView mv = new ModelAndView("profile/profile");
        mv.addObject("user", user);

        return mv;
    }

    @GetMapping("/update")
    public ModelAndView openUpdatePage(Principal principal,  @AuthenticationPrincipal OAuth2User oauth2User){
        User user = getPrincipalUser(principal, oauth2User);
        ModelAndView mv = new ModelAndView("profile/update");
        mv.addObject("user", user);

        return mv;
    }

    @PostMapping("/update")
    public ModelAndView updateProfile(@ModelAttribute("user") @Valid User userDto, BindingResult result, Principal principal, @AuthenticationPrincipal OAuth2User oauth2User){
        User user = getPrincipalUser(principal, oauth2User);
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setCity(userDto.getCity());
        user.setAddress(userDto.getAddress());

        userService.save(user);

        ModelAndView mv = new ModelAndView("profile/profile");
        return mv;
    }

    public User getPrincipalUser(Principal principal, OAuth2User oAuth2User) {

        User user;

        if (oAuth2User != null) {
            GoogleUserInfo googleUserInfo = new GoogleUserInfo(oAuth2User.getAttributes());

            user = userService.findByEmail(googleUserInfo.getEmail());
        } else {
            user = userService.findByEmail(principal.getName());
        }


        return user;
    }
}

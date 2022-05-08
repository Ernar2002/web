package com.shop.onlineshop.controller;

import com.shop.onlineshop.model.GoogleUserInfo;
import com.shop.onlineshop.model.entities.Product;
import com.shop.onlineshop.model.entities.User;
import com.shop.onlineshop.service.EmailSenderService;
import com.shop.onlineshop.service.ProductService;
import com.shop.onlineshop.service.UserService;
import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class OrderController {

    private final ProductService productService;

    private UserService userService;

    private EmailSenderService senderService;

    @Autowired
    public OrderController(ProductService productService, UserService userService, EmailSenderService senderService) {
        this.userService = userService;
        this.senderService = senderService;
        this.productService = productService;
    }

    @GetMapping("/list")
    public ModelAndView cartProduct(Principal principal, @AuthenticationPrincipal OAuth2User oauth2User) {

        User user = getPrincipalUser(principal, oauth2User);

        ModelAndView mv = new ModelAndView("profile/cart/cart-product");
        mv.addObject("user", user);
        int total = findSum(user);
        mv.addObject("total", total);
        return mv;
    }

    @GetMapping("/add-to-cart/{productId}")
    public ModelAndView addToCart(@PathVariable("productId") String productId, Principal principal, @AuthenticationPrincipal OAuth2User oauth2User) {

        User user = getPrincipalUser(principal, oauth2User);

        ModelAndView mv = new ModelAndView("profile/cart/cart-product");

        long productLongId = Long.parseLong(productId);
        Product product = productService.getProductById(productLongId).get();

        List<Product> productList = new ArrayList<Product>();
        productList.add(product);
        user.setProductList(productList);

        List<User> userList = new ArrayList<>();
        userList.add(user);
        product.setUserList(userList);

        userService.update(user);
        productService.addProduct(product);
        int total = findSum(user);
        mv.addObject("total", total);
        mv.addObject("user", user);
        return mv;
    }

    @GetMapping("/delete-from-cart/{productId}")
    public String deleteFromCart(@PathVariable("productId") String productId, Principal principal, @AuthenticationPrincipal OAuth2User oauth2User) {
        User user = getPrincipalUser(principal, oauth2User);

        long productLongId = Long.parseLong(productId);
        Product deletedProduct = productService.getProductById(productLongId).get();

        userService.deleteProductFromUserProductList(user.getUserId(), deletedProduct.getProductId());

        return "redirect:/cart/list";
    }

    @GetMapping("/purchase")
    public String purchase(Principal principal, @AuthenticationPrincipal OAuth2User oauth2User) {
        User user = getPrincipalUser(principal, oauth2User);

        senderService.sendEmail(user.getEmail(),"Order","Purchase completed successfully\n", user.getProductList(), findSum(user));
        userService.deleteAllProductsFromUserProductList(user.getUserId());

        return "redirect:/cart/list";
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

    public int findSum(User user) {
        List<Product> list = user.getProductList();
        int sum = 0;
        for (int i = 0; i < list.size(); i++) {
            Product p = list.get(i);
            sum += p.getProductPrice();
        }
        return sum;
    }

}

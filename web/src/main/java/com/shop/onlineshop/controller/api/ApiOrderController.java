package com.shop.onlineshop.controller.api;

import com.shop.onlineshop.model.entities.Product;
import com.shop.onlineshop.model.entities.User;
import com.shop.onlineshop.service.EmailSenderService;
import com.shop.onlineshop.service.ProductService;
import com.shop.onlineshop.service.UserService;
import com.shop.onlineshop.utils.ProductDto;
import com.shop.onlineshop.utils.responses.OrderResponse;
import com.shop.onlineshop.utils.responses.TotalSumResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiOrderController {

    private final ProductService productService;

    private UserService userService;

    private EmailSenderService senderService;

    @Autowired
    public ApiOrderController(ProductService productService, UserService userService, EmailSenderService senderService) {
        this.userService = userService;
        this.senderService = senderService;
        this.productService = productService;
    }

    @GetMapping("/get-cart-products")
    public List<ProductDto> getCartList(@RequestParam String email){
        User user = userService.findByEmail(email);
        List<ProductDto> productDtoList = new ArrayList<>();

        if(user != null){
            List<Product> productList = user.getProductList();

            for(Product product: productList){
                productDtoList.add(ProductDto.fromProduct(product));
            }

            return productDtoList;
        }

        return null;
    }

    @GetMapping("/get-total-sum")
    public TotalSumResponse getTotalSum(@RequestParam String email){
        User user = userService.findByEmail(email);

        TotalSumResponse response = new TotalSumResponse();

        if(user != null){
            int totalSum = findSum(user);

            response.setTotalSum(totalSum);
            response.setError(false);
        } else response.setError(true);

        return response;
    }

    @GetMapping("/add-to-cart")
    public OrderResponse addToCart(@RequestParam Long productId,
                                   @RequestParam String email) {
        User user = userService.findByEmail(email);

        Product product = productService.getProductById(productId).orElse(null);

        OrderResponse orderResponse = new OrderResponse();
        if(product != null) {
            List<Product> productList = new ArrayList<>();
            productList.add(product);
            user.setProductList(productList);

            List<User> userList = new ArrayList<>();
            userList.add(user);
            product.setUserList(userList);

            userService.update(user);
            productService.addProduct(product);

            orderResponse.setError(false);
            orderResponse.setMessage("Product added to cart");
        } else {
            orderResponse.setError(true);
            orderResponse.setMessage("Product not found");
        }

        return orderResponse;
    }

    @GetMapping("/delete-from-cart")
    public OrderResponse deleteFromCart(@RequestParam Long productId,
                                        @RequestParam String email) {
        User user = userService.findByEmail(email);
        Product deletedProduct = productService.getProductById(productId).orElse(null);

        OrderResponse response = new OrderResponse();
        if(user!=null && deletedProduct!=null) {
            userService.deleteProductFromUserProductList(user.getUserId(), deletedProduct.getProductId());
            response.setError(false);
            response.setMessage("Product deleted from cart");
        } else {
            response.setMessage("Product not found");
            response.setError(true);
        }

        return response;
    }

    @GetMapping("/purchase")
    public OrderResponse purchase(@RequestParam String email){
        User user = userService.findByEmail(email);

        OrderResponse orderResponse = new OrderResponse();
        if(user!=null) {
            senderService.sendEmail(email, "Order", "Purchase completed successfully\n", user.getProductList(), findSum(user));
            userService.deleteAllProductsFromUserProductList(user.getUserId());
            orderResponse.setError(false);
            orderResponse.setMessage("Purchased successfully");

            return orderResponse;
        } else {
            orderResponse.setError(true);
            orderResponse.setMessage("User not found");
        }

        return orderResponse;
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

package com.shop.onlineshop.service;

import com.shop.onlineshop.model.entities.User;

import java.util.List;


public interface UserService {
	
	User findByEmail(String email);
	
	void save(User user);
	
	void update(User user);

	User getById(Long id);

	List<User> findAllUser();
	
	void deleteUser(long userId);

	void deleteProductFromUserProductList(Long userId, Long productId);

	void deleteAllProductsFromUserProductList(Long userId);

}

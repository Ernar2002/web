package com.shop.onlineshop.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import com.shop.onlineshop.model.entities.Product;
import com.shop.onlineshop.repositories.UserRepository;
import com.shop.onlineshop.model.entities.User;
import com.shop.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public User getById(Long id) {
		return userRepository.getById(id);
	}

	@Override
	public void update(User user) {
		List<Product> productlist1 = user.getProductList();
		List<Product> productlist = (userRepository.findByEmail(user.getEmail())).getProductList();
		productlist1.addAll(productlist);
		user.setProductList(productlist1);
		userRepository.save(user);
	}

	@Override
	public List<User> findAllUser() {
		return userRepository.findAll();
	}

	@Override
	public void deleteUser(long userId) {
		userRepository.deleteById(userId);
	}

	@Override
	public void deleteProductFromUserProductList(Long userId, Long productId) {
		userRepository.deleteProductFromUserProductList(userId, productId);
	}

	@Override
	public void deleteAllProductsFromUserProductList(Long userId) {
		userRepository.deleteAllProductsFromUserProductList(userId);
	}

}

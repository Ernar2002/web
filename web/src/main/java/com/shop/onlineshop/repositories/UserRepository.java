package com.shop.onlineshop.repositories;

import com.shop.onlineshop.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);

	@Modifying
	@Query(value = "DELETE FROM user_product_list where user_id = :userId AND product_id = :productId", nativeQuery = true)
	void deleteProductFromUserProductList(@Param("userId") Long userId, @Param("productId") Long productId);

	@Modifying
	@Query(value = "DELETE from user_product_list where user_id = :userId", nativeQuery = true)
	void deleteAllProductsFromUserProductList(@Param("userId") Long userId);
}

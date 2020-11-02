package com.tomtom.ecommerce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tomtom.ecommerce.entity.CartDetails;
import com.tomtom.ecommerce.entity.CartDetailsKey;

@Repository
public interface CartDetailsRepo extends JpaRepository<CartDetails, CartDetailsKey> {

	@Query("select c from cart_details c where cartid= ?1 ")
	List<CartDetails> getCartDetails(String cartId);
	
	

}

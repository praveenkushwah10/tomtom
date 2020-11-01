package com.tomtom.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tomtom.ecommerce.entity.CartInfo;

@Repository
public interface CartRepo extends JpaRepository<CartInfo, String> {
	
	@Query("select c from cart_info c where  user_id= ?1")
	CartInfo getcartInfoForUseid(String userid);

}

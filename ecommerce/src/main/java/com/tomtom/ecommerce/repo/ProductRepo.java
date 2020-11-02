package com.tomtom.ecommerce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tomtom.ecommerce.entity.ProductInfo;

@Repository
public interface ProductRepo extends JpaRepository<ProductInfo, String> {
	
	
	@Query("select p from product_info p WHERE product_id = ?1")
	ProductInfo getProductById(String productId);

	
	@Query("select p from product_info p")
	List<ProductInfo> getAllProducts();
}

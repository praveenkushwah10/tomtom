package com.tomtom.ecommerce.service;

import java.util.List;

import com.tomtom.ecommerce.exception.BadRequestException;
import com.tomtom.ecommerce.models.Product;


public interface ProductService {
	
	public List<Product> getAllProducts();
	
	public Product getProduct(String productId);

	public void addProduct(Product newProduct, String jwtToken) throws BadRequestException;
	

}

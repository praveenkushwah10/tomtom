package com.tomtom.ecommerce.service;

import java.util.List;

import com.tomtom.ecommerce.exception.BadRequestException;
import com.tomtom.ecommerce.models.Product;
import com.tomtom.ecommerce.models.Response;


public interface ProductService {
	
	public Response<List<Product>> getAllProducts();
	
	public Response<Product> getProduct(String productId);

	public Response<Product> addProduct(Product newProduct, String jwtToken) throws BadRequestException;
	

}

package com.tomtom.ecommerce.models;

import java.util.List;

public class Cart {

	private String cartId;
	
	private List<Product> products;

	public String getCartId() {
		return cartId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	
}

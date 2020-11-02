package com.tomtom.ecommerce.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity(name="cart_details")
public class CartDetails implements Serializable{
	
	@EmbeddedId
	private CartDetailsKey key;
	
	private int count;

	
	public CartDetailsKey getKey() {
		return key;
	}

	public void setKey(CartDetailsKey key) {
		this.key = key;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
	
}

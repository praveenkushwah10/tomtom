package com.tomtom.ecommerce.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name="cart_info")
public class CartInfo {

	@Id
	private String cartId;
	
	@OneToOne
	@JoinColumn(name="user_id")
	private UserInfo userinfo;
	
	private Date createdDate;
	
	private boolean cartSubmitted=false;

	
	public boolean isCartSubmitted() {
		return cartSubmitted;
	}

	public void setCartSubmitted(boolean cartSubmitted) {
		this.cartSubmitted = cartSubmitted;
	}

	public String getCartId() {
		return cartId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId;
	}

	public UserInfo getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
	
	
	
	
}

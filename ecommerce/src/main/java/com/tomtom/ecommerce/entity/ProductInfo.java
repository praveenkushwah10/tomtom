package com.tomtom.ecommerce.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="product_info")
public class ProductInfo {
	
	@Id
	private String productId;
	
	private String productName;
	
	private int price;
	
	private String sellerid;
	

	public String getSellerid() {
		return sellerid;
	}

	public void setSellerid(String sellerid) {
		this.sellerid = sellerid;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	
	

}

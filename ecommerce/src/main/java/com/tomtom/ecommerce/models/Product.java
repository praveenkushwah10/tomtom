package com.tomtom.ecommerce.models;

import com.tomtom.ecommerce.entity.ProductInfo;

public class Product {

	private String productName;
	
	private String productId;
	
	private int price;
	
	private int quantity;
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Product(String productName, String productId, int price) {
		super();
		this.productName = productName;
		this.productId = productId;
		this.price = price;
	}
	
	public Product() {
		super();
	}

	public Product(ProductInfo productInfo)
	{
		super();
		this.productName = productInfo.getProductName();
		this.productId = productInfo.getProductId();
		this.price = productInfo.getPrice();
	}

	public int getPrice() {
		return price;
	}

	public String getProductName() {
		return productName;
	}

	public String getProductId() {
		return productId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		return true;
	}


	
	
	
}

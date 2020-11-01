package com.tomtom.ecommerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tomtom.ecommerce.entity.ProductInfo;
import com.tomtom.ecommerce.exception.BadRequestException;
import com.tomtom.ecommerce.models.Product;
import com.tomtom.ecommerce.repo.ProductRepo;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepo productRepo;
	
	@Override
	public List<Product> getAllProducts() {

		List<ProductInfo> productInfoList = productRepo.getAllProducts();
		
		return productInfoList.stream().map(productinfo-> new Product(productinfo)).collect(Collectors.toList());
	}

	@Override
	public Product getProduct(String productId) {
		ProductInfo productInfo= productRepo.getProductById(productId);
		return new Product(productInfo);
	}

	@Override
	public void addProduct(Product newProduct, String jwtToken) throws BadRequestException {

		String sellerId= validateToken(jwtToken);
		
		if(newProduct == null)
			throw new BadRequestException("Invalid product details");
		if(StringUtils.isEmpty(newProduct.getProductId()) ||
				StringUtils.isEmpty(newProduct.getProductName()))
			throw new BadRequestException("Invalid product id/name");
		
		ProductInfo productInfo = new ProductInfo();
		productInfo.setPrice(newProduct.getPrice());
		productInfo.setProductId(newProduct.getProductId());
		productInfo.setProductName(newProduct.getProductName());
		productInfo.setSellerid(sellerId);
		productRepo.save(productInfo);
		
	}

	private String validateToken(String jwtToken) {
		//Assuming token is valid seller
		return "seller01";
	}

}

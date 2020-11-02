package com.tomtom.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.tomtom.ecommerce.entity.ProductInfo;
import com.tomtom.ecommerce.models.MessageModel;
import com.tomtom.ecommerce.models.Product;
import com.tomtom.ecommerce.models.Response;
import com.tomtom.ecommerce.repo.ProductRepo;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepo productRepo;
	
	@Override
	public Response<List<Product>> getAllProducts() {

		List<ProductInfo> productInfoList = productRepo.getAllProducts();

		Response<List<Product>> response = new Response<>();
		if (CollectionUtils.isEmpty(productInfoList)) {
			List<MessageModel> errorList = new ArrayList<>();
			errorList.add(new MessageModel("No Products Found", "ONL_PRO_001"));
			response.setErrors(errorList);
			response.setHttpStatus(HttpStatus.NOT_FOUND.value());
		} else {
			response.setData(
					productInfoList.stream().map(productinfo -> new Product(productinfo)).collect(Collectors.toList()));
			response.setHttpStatus(HttpStatus.OK.value());
		}
		return response;
	}

	@Override
	public Response<Product> getProduct(String productId) {
		Response<Product> response = new Response<>();
		ProductInfo productInfo= productRepo.getProductById(productId);
		if(productInfo == null)
		{
			List<MessageModel> errorList = new ArrayList<>();
			errorList.add(new MessageModel("Products not found with id:"+productId, "ONL_PRO_404_001"));
			response.setErrors(errorList);
			response.setHttpStatus(HttpStatus.NOT_FOUND.value());
		}else {
			response.setData(new Product(productInfo));
		}
		
		return response;
	}

	@Override
	public  Response<Product> addProduct(Product newProduct, String jwtToken) {

		String sellerId= validateToken(jwtToken);
		Response<Product> response = new Response<>();
		
		if(StringUtils.isEmpty(newProduct.getProductId()) ||
				StringUtils.isEmpty(newProduct.getProductName()))
		{	
			List<MessageModel> errorList = new ArrayList<>();
			errorList.add(new MessageModel("Invalid product id/name", "ONL_PRO_400_001"));
			response.setErrors(errorList);
			response.setHttpStatus(HttpStatus.NOT_FOUND.value());
			return response;
		}
		ProductInfo productInfo = new ProductInfo();
		productInfo.setPrice(newProduct.getPrice());
		productInfo.setProductId(newProduct.getProductId());
		productInfo.setProductName(newProduct.getProductName());
		productInfo.setSellerid(sellerId);
		productRepo.save(productInfo);
		response.setHttpStatus(HttpStatus.CREATED.value());
		return response;
	}

	private String validateToken(String jwtToken) {
		//Assuming token is valid seller
		return "seller01";
	}

}

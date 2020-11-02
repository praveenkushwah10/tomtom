package com.tomtom.ecommerce.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.tomtom.ecommerce.entity.CartDetails;
import com.tomtom.ecommerce.entity.CartDetailsKey;
import com.tomtom.ecommerce.entity.CartInfo;
import com.tomtom.ecommerce.entity.ProductInfo;
import com.tomtom.ecommerce.entity.UserInfo;
import com.tomtom.ecommerce.models.Cart;
import com.tomtom.ecommerce.models.MessageModel;
import com.tomtom.ecommerce.models.Payment;
import com.tomtom.ecommerce.models.Product;
import com.tomtom.ecommerce.models.Response;
import com.tomtom.ecommerce.repo.CartDetailsRepo;
import com.tomtom.ecommerce.repo.CartRepo;
import com.tomtom.ecommerce.repo.ProductRepo;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepo cartRepo;

	@Autowired
	private CartDetailsRepo cartDetailsRepo;

	@Autowired
	private ProductRepo productRepo;

	@Override
	public Response<Cart> createCart(Cart newCart, String jwtToken) throws Exception {

		Response<Cart> response = new Response<>();
		List<MessageModel> errorList = new ArrayList<>();
		UserInfo userInfo = validtetoken(jwtToken);

		CartInfo existingCart = cartRepo.getcartInfoForUseid(userInfo.getUserId());
		if (existingCart != null) {
			newCart.setCartId(existingCart.getCartId());
			saveCartDetails(newCart, existingCart.getCartId(), errorList);
			List<CartDetails> cartDetailsList = cartDetailsRepo.getCartDetails(existingCart.getCartId());
			newCart.setProducts(getProductList(cartDetailsList));

		} else {
			CartInfo newCartInfo = new CartInfo();
			newCartInfo.setCartId(UUID.randomUUID().toString());
			newCartInfo.setUserinfo(userInfo);
			newCart.setCartId(newCartInfo.getCartId().toString());
			cartRepo.save(newCartInfo);
			saveCartDetails(newCart, newCartInfo.getCartId(), errorList);
		}
		calculateTotal(newCart);
		response.setData(newCart);
		response.setErrors(errorList);
		response.setHttpStatus(HttpStatus.CREATED.value());
		return response;
	}

	private void calculateTotal(Cart newCart) {
		int total = newCart.getProducts().stream().map(p -> p.getQuantity() * p.getPrice()).reduce(0, Integer::sum);
		newCart.setTotal(total);
	}

	private List<Product> getProductList(List<CartDetails> cartDetailsList) {
		List<Product> productList = new ArrayList<>();
		cartDetailsList.stream().forEach(cartDetail -> {
			Product product = new Product(productRepo.getProductById(cartDetail.getKey().getProductId()));
			product.setQuantity(cartDetail.getCount());
			productList.add(product);
		});
		return productList;
	}

	private UserInfo validtetoken(String jwtToken) {
		// Assuming token is valid
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId("user01");
		return userInfo;
	}

	private void saveCartDetails(Cart newCart, String cartid, List<MessageModel> errorList) {
		HashMap<Product, Integer> productMap = new HashMap<>();
		newCart.getProducts().forEach(product -> {

			ProductInfo productInfo = productRepo.getProductById(product.getProductId());
			if (productInfo == null) {
				errorList.add(new MessageModel("Invalid Product id:" + product.getProductId(), "ONL_PRO_400_03"));
			} else {
				Product newProduct = new Product(productInfo);
				if (productMap.containsKey(newProduct)) {
					int newCount = productMap.get(newProduct).intValue() + product.getQuantity();
					productMap.put(newProduct, newCount);
					newProduct.setQuantity(newCount);
				} else {
					newProduct.setQuantity(product.getQuantity());
					productMap.put(newProduct, product.getQuantity());
				}
			}
		});
		
		List<Product> productlist = new ArrayList<>();
		productMap.entrySet().forEach(productEntry -> {
			CartDetails cartDetails = new CartDetails();
			CartDetailsKey key = new CartDetailsKey();
			key.setCartid(cartid);
			key.setProductId(productEntry.getKey().getProductId());
			cartDetails.setKey(key);
			cartDetails.setCount(productEntry.getKey().getQuantity());
			cartDetailsRepo.save(cartDetails);
			productlist.add(productEntry.getKey());
		});
		newCart.setProducts(productlist);
	}

	@Override
	public Response<Cart> getCart(String carid) {
		Response<Cart> response = new Response<>();
		Cart cart = new Cart();
		List<CartDetails> cartDetailsList = cartDetailsRepo.getCartDetails(carid);
		if (CollectionUtils.isEmpty(cartDetailsList)) {
			List<MessageModel> errorList = new ArrayList<>();
			errorList.add(new MessageModel("Invalid cart id", "ONL_CART_400_001"));
			response.setErrors(errorList);
			response.setHttpStatus(HttpStatus.NOT_FOUND.value());
			return response;
		}
		cart.setProducts(getProductList(cartDetailsList));
		cart.setCartId(carid);
		calculateTotal(cart);
		response.setData(cart);
		response.setHttpStatus(HttpStatus.OK.value());
		return response;
	}

	@Override
	public Response<String> submitCart(Payment payment, String cartid, String jwtToken) {
		UserInfo userInfo = validtetoken(jwtToken);
		Response<String> response = new Response<>();
		CartInfo existingCart = cartRepo.getcartInfoForUseid(userInfo.getUserId());
		if (existingCart != null) {
			existingCart.setCartSubmitted(true);
			cartRepo.save(existingCart);
		}
		response.setData("Cart submitted and payment successfull");
		response.setHttpStatus(HttpStatus.OK.value());
		return response;
	}

}

package com.tomtom.ecommerce.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomtom.ecommerce.entity.CartDetails;
import com.tomtom.ecommerce.entity.CartDetailsKey;
import com.tomtom.ecommerce.entity.CartInfo;
import com.tomtom.ecommerce.entity.UserInfo;
import com.tomtom.ecommerce.models.Cart;
import com.tomtom.ecommerce.models.Payment;
import com.tomtom.ecommerce.models.Product;
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
	public Cart createCart(Cart newCart, String jwtToken) throws Exception{

		UserInfo userInfo = validtetoken(jwtToken);

		CartInfo existingCart = cartRepo.getcartInfoForUseid(userInfo.getUserId());
		if (existingCart != null) {
			newCart.setCartId(existingCart.getCartId());
			saveCartDetails(newCart, existingCart.getCartId());
			List<CartDetails> cartDetailsList = cartDetailsRepo.getCartDetails(existingCart.getCartId());
			newCart.setProducts(getProductList(cartDetailsList));

		} else {
			CartInfo newCartInfo = new CartInfo();
			newCartInfo.setCartId(UUID.randomUUID().toString());
			newCartInfo.setUserinfo(userInfo);
			newCart.setCartId(newCartInfo.getCartId().toString());
			cartRepo.save(newCartInfo);
			saveCartDetails(newCart, newCartInfo.getCartId());
		}
		calculateTotal(newCart);
		return newCart;
	}

	private void calculateTotal(Cart newCart) {
		int total=	newCart.getProducts().stream().map(p-> p.getCount()* p.getPrice()).reduce(0, Integer::sum);	
		newCart.setTotal(total);
	}
	
	private List<Product> getProductList(List<CartDetails> cartDetailsList ) {
		List<Product> productList = new ArrayList<>();
		cartDetailsList.stream().forEach(cartDetail -> {
			Product product = new Product(productRepo.getProductById(cartDetail.getKey().getProductId()));
			product.setCount(cartDetail.getCount());
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

	private void saveCartDetails(Cart newCart, String cartid) {
		HashMap<Product, Integer> productMap = new HashMap<>();
		newCart.getProducts().forEach(product -> {
			
			
			if (productMap.containsKey(product)) {
				int newCount = productMap.get(product).intValue() + product.getCount();
				productMap.put(product, newCount);
			} else {
				productMap.put(product, product.getCount());
			}
		});
		productMap.entrySet().forEach(productEntry -> {
			CartDetails cartDetails = new CartDetails();
			CartDetailsKey key = new CartDetailsKey();
			key.setCartid(cartid);
			key.setProductId(productEntry.getKey().getProductId());
			cartDetails.setKey(key);
			cartDetails.setCount(productEntry.getKey().getCount());
			cartDetailsRepo.save(cartDetails);
		});
	}

	@Override
	public Cart getCart(String carid) {
		Cart cart = new Cart();
		List<CartDetails> cartDetailsList = cartDetailsRepo.getCartDetails(carid);
		cart.setProducts(getProductList(cartDetailsList));
		cart.setCartId(carid);
		calculateTotal(cart);
		return cart;
	}

	@Override
	public void submitCart(Payment payment, String cartid, String jwtToken) {
		UserInfo userInfo = validtetoken(jwtToken);

		CartInfo existingCart = cartRepo.getcartInfoForUseid(userInfo.getUserId());
		if(existingCart!= null)
		{
			existingCart.setCartSubmitted(true);
			cartRepo.save(existingCart);
		}
		
	}

}

package com.tomtom.ecommerce.service;

import com.tomtom.ecommerce.models.Cart;
import com.tomtom.ecommerce.models.Payment;
import com.tomtom.ecommerce.models.Response;

public interface CartService {

	Response<Cart> createCart(Cart newCart, String jwtToken) throws Exception;

	Response<Cart> getCart(String carid);

	Response<String> submitCart(Payment payment, String cartid, String jwtToken);

}

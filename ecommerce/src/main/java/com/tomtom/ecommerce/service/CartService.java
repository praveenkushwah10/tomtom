package com.tomtom.ecommerce.service;

import com.tomtom.ecommerce.models.Cart;
import com.tomtom.ecommerce.models.Payment;

public interface CartService {

	Cart createCart(Cart newCart, String jwtToken) throws Exception;

	Cart getCart(String carid);

	void submitCart(Payment payment, String cartid, String jwtToken);

}

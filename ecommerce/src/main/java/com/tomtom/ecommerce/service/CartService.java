package com.tomtom.ecommerce.service;

import com.tomtom.ecommerce.models.Cart;

public interface CartService {

	Cart createCart(Cart newCart, String jwtToken);

}

package com.tomtom.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tomtom.ecommerce.models.Cart;
import com.tomtom.ecommerce.models.Payment;
import com.tomtom.ecommerce.models.Response;
import com.tomtom.ecommerce.service.CartService;

@Controller
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	 @GetMapping(path="/cart/{cartid}", produces = "application/json" )
		public @ResponseBody Response<Cart> getCart(@PathVariable String cartid) {
			
		 return cartService.getCart(cartid);
		}
	 
	 @PostMapping(path="/cart", produces = "application/json" )
		public @ResponseBody Response<Cart> createNewCart(@RequestBody Cart newCart, @RequestHeader String jwtToken ) throws Exception {
			
		 return cartService.createCart(newCart,jwtToken);
		}
	 
	 @PostMapping(path="/cart/submit/{cartid}", produces = "application/json" )
		public @ResponseBody Response<String> submitCart(@RequestBody Payment payment, @RequestHeader String jwtToken,@PathVariable String cartid ) {
		 return cartService.submitCart(payment,cartid,jwtToken);
		}
}

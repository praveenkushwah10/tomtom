package com.tomtom.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tomtom.ecommerce.models.Cart;
import com.tomtom.ecommerce.models.Payment;
import com.tomtom.ecommerce.service.CartService;

@Controller
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	 @GetMapping(path="/cart/{cartid}", produces = "application/json" )
		public @ResponseBody ResponseEntity<Cart> getCart(@PathVariable String cartid) {
			
		 return new ResponseEntity<>(cartService.getCart(cartid), HttpStatus.OK);
		}
	 
	 @PostMapping(path="/cart", produces = "application/json" )
		public @ResponseBody ResponseEntity<Cart> createNewCart(@RequestBody Cart newCart, @RequestHeader String jwtToken ) throws Exception {
			
		 return new ResponseEntity<>(cartService.createCart(newCart,jwtToken), HttpStatus.OK);
		}
	 
	 @PostMapping(path="/cart/submit/{cartid}", produces = "application/json" )
		public @ResponseBody ResponseEntity<String> submitCart(@RequestBody Payment payment, @RequestHeader String jwtToken,@PathVariable String cartid ) {
		 cartService.submitCart(payment,cartid,jwtToken);
		 return new ResponseEntity<>("Payment was successfull",HttpStatus.OK);
		}
}

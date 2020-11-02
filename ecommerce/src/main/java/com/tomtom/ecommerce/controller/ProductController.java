package com.tomtom.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tomtom.ecommerce.exception.BadRequestException;
import com.tomtom.ecommerce.models.Product;
import com.tomtom.ecommerce.service.ProductService;


@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	 @GetMapping(path="/products/all", produces = "application/json" )
		public @ResponseBody ResponseEntity<List<Product>> getAllProducts(@RequestParam int offset, @RequestParam int limit) {
			return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
		}
	 
	 @GetMapping(path="/products/{productid}", produces = "application/json" )
		public @ResponseBody ResponseEntity<Product> getProduct(@PathVariable String productid) {
			return new ResponseEntity<>(productService.getProduct(productid), HttpStatus.OK);
		}
	 
	 @PostMapping(path="/products", consumes = "application/json" )
	 public @ResponseBody ResponseEntity<Product> addProduct(@RequestBody Product newProduct, @RequestParam String jwtToken ) throws BadRequestException
	 {
		 productService.addProduct(newProduct, jwtToken);
			return new ResponseEntity<>(HttpStatus.CREATED);
	 }

}

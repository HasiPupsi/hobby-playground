package com.springboot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.data.Product;

@org.springframework.web.bind.annotation.RestController
public class RestController {

	private List<Product> productList = new ArrayList<>();

	@RequestMapping(value = "/")
	public ResponseEntity<Object> defaultRequest() {
		return new ResponseEntity<Object>("Anfrage wurde verarbeitet!", HttpStatus.OK);
	}

	@RequestMapping(value = "/getProducts")
	public ResponseEntity<List<Product>> getProducts() {
		return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
	}

	@RequestMapping(value = "/createProduct")
	public ResponseEntity<Object> createProduct(@RequestBody Product product) {
		productList.add(product);
		return new ResponseEntity<Object>(HttpStatus.CREATED);
	}

}

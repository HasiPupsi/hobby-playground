package com.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.springboot.data.Product;

@Component
public class RequestHandler {

	@Autowired
	private RestTemplate restTemplate;

	public ResponseEntity<Object> addProduct(String url, Product product) {
		return restTemplate.postForEntity(url, product, Object.class);
	}

	public ResponseEntity<Product[]> getProducts(String url) {
		return restTemplate.getForEntity(url, Product[].class);
	}

}

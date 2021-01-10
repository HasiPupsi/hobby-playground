package com.springboot;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.springboot.data.Product;

@SpringBootApplication
public class Application implements ApplicationRunner {

	@Autowired
	private ApplicationContext context;

	public static void main(String[] args) {
		SpringApplication springApp = new SpringApplication(Application.class);
		springApp.setWebEnvironment(false);
		springApp.run(args);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("Applicationrunner start");
		RequestHandler requestHandler = (RequestHandler) context.getBean("requestHandler");
		handleResponseEntity(requestHandler.getProducts("http://localhost:8081/getProducts"));
		Product p = new Product();
		p.setName("Test");
		p.setPrice(10.00);
		p.setBarcode("123456789");
		handleResponseEntity(requestHandler.addProduct("http://localhost:8081/createProduct", p));
		System.out.println("Applicationrunner end");
	}

	public void handleResponseEntity(ResponseEntity response) {
		switch (response.getStatusCode()) {
		case OK:
			Product[] products = (Product[]) response.getBody();
			Arrays.asList(products).forEach(product -> {
				System.out.println(product);
			});
		case CREATED:
			System.out.println("Request war erfolgreich!!!!(" + response.getStatusCode().name() + ")");
			break;
		default:
			System.out.println("Unbekannter Status -> Fehler(" + response.getStatusCode() + ")");
			break;
		}
	}

}

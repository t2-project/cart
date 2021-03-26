package de.unistuttgart.t2.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import de.unistuttgart.t2.cart.repository.CartRepository;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = CartRepository.class)
public class CartApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartApplication.class, args);
	}

}

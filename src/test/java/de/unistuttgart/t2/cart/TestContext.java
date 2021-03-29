package de.unistuttgart.t2.cart;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import de.unistuttgart.t2.cart.repository.CartRepository;

@Configuration
@EnableAutoConfiguration
@EnableMongoRepositories(basePackageClasses = {CartRepository.class})
public class TestContext {
	
}

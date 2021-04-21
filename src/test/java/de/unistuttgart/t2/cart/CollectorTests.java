package de.unistuttgart.t2.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.unistuttgart.t2.cart.repository.CartItem;
import de.unistuttgart.t2.cart.repository.CartRepository;
import de.unistuttgart.t2.cart.repository.TimeoutCollector;
import de.unistuttgart.t2.common.CartContent;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestContext.class)
@SpringBootTest
@ActiveProfiles("test")
class CollectorTests {
	
	private long initialSize;
	
	@Autowired CartRepository repository;
	
	@Autowired TimeoutCollector collector;
	
	// defaults: 
	// TTL = 20 sec
	// taskRate = 20000 milli sec = 20 sec
	
	@BeforeEach
	public void populateRepository() {
		repository.deleteAll();
	}
	
	
	@Test
	public void collectSingleEntryTest() throws InterruptedException {
		CartItem emptyCart = new CartItem("foo");
		repository.save(emptyCart);
		initialSize = repository.findAll().size();
		
		Thread.sleep(20000);
		
		assertEquals(initialSize - 1, repository.count());	
	}
	
	@Test
	public void collectMultipleEntryTest() throws InterruptedException {
		for (int i = 0; i < 5; i++) {
			repository.save(new CartItem());			
		}
		
		initialSize = repository.count();
		
		Thread.sleep(10000);
		assertEquals(initialSize, repository.count());
		
		Thread.sleep(10000);
		assertEquals(initialSize - 5, repository.count());
	}
}

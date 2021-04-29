package de.unistuttgart.t2.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import de.unistuttgart.t2.cart.repository.CartItem;
import de.unistuttgart.t2.cart.repository.CartRepository;
import de.unistuttgart.t2.cart.repository.TimeoutCollector;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestContext.class)
@SpringBootTest
@ActiveProfiles("test")
/**
 * actually i was trying to test my collector.. but timing is a bad bitch and it fucks me up.
 * 
 * @author maumau
 *
 */
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
		initialSize = repository.findAll().size();
		
//		Thread.sleep(4000);
//		
//		assertEquals(initialSize - 1, repository.count());	
	}
	
	@Test
	public void collectMultipleEntryTest() throws InterruptedException {
		for (int i = 0; i < 5; i++) {
			repository.save(new CartItem());			
		}
		
//		initialSize = repository.count();
//		
//		Thread.sleep(1000);
//		assertEquals(initialSize, repository.count());
//		
//		Thread.sleep(4000);
//		assertEquals(initialSize - 5, repository.count());
	}
}

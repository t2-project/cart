package de.unistuttgart.t2.cart;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.unistuttgart.t2.cart.domain.CartEntry;
import de.unistuttgart.t2.cart.repository.CartRepository;
import de.unistuttgart.t2.common.domain.CartContent;

@RestController
public class CartController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	CartRepository cartRepository;
    
	@GetMapping(value = "/get/{sessionId}")
	@ResponseBody
    public CartContent getCart(@PathVariable String sessionId) {
		// TODO get actual shippment 
		LOG.info("received get request");
		Optional<CartEntry> optentry = cartRepository.findById(sessionId);
		CartEntry entry = optentry.orElse(new CartEntry(sessionId));
        return new CartContent(entry.getContent());
    }
	
	@PutMapping(value = "/test")
	public void test(@RequestBody String s) {
		LOG.info(s);
	
	}
	
	@PutMapping(value = "/add/{sessionId}/{productId}/{units}")
	public void addToCart(@PathVariable String sessionId, @PathVariable String productId, @PathVariable Integer units ) {
		// TODO make this work with requestbody.
		LOG.info("received put request : " + sessionId + " " + productId + " " + units);

		Optional<CartEntry> optentry = cartRepository.findById(sessionId);
		CartEntry entry = optentry.orElse(new CartEntry(sessionId));
		
		entry.updateProduct(productId, units);
		cartRepository.save(entry);
	}
	
}

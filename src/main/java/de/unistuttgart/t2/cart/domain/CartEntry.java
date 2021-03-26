package de.unistuttgart.t2.cart.domain;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;

public class CartEntry {
	@Id
	private String id;
	private Map<String, Integer> content;
	
	public CartEntry(String id, Map<String, Integer> content) {
		super();
		this.id = id;
		this.content = content;
	}
	
	public CartEntry(String id) {
		super();
		this.id = id;
		this.content = new HashMap<>();
	}
	
	public CartEntry() {
		super();
		this.content = new HashMap<>();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Map<String, Integer> getContent() {
		return content;
	}
	public void setContent(Map<String, Integer> content) {
		this.content = content;
	}
	
	public void updateProduct(String productId, Integer units) {
		Integer current = 0;
		if(content.containsKey(productId)) {
			current = content.get(productId);
		}
		content.put(productId, current + units);
	}

}

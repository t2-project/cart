package de.unistuttgart.t2.cart.domain;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;

public class CartItem {
	@Id
	private String id;
	private Map<String, Integer> content;
	
	public CartItem(String id, Map<String, Integer> content) {
		super();
		this.id = id;
		this.content = content;
	}
	
	public CartItem(String id) {
		super();
		this.id = id;
		this.content = new HashMap<>();
	}
	
	public CartItem() {
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
}

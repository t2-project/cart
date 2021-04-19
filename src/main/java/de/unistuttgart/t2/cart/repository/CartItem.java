package de.unistuttgart.t2.cart.repository;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;

public class CartItem {
	@Id
	private String id;
	private Map<String, Integer> content;
	
	private Date creationDate; 
	
	public CartItem(String id, Map<String, Integer> content) {
		super();
		this.id = id;
		this.content = content;
		creationDate = Date.from(Instant.now()); // ???
	}
	
	public CartItem(String id) {
		this(id, new HashMap<>());
	}
	
	public CartItem() {
		this(null, new HashMap<>());
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}

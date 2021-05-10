package de.unistuttgart.t2.cart.repository;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;

/**
 * 
 * The content of someones cart. 
 * 
 * <p>
 * Cart items have a {@code creationDate} such that they might be killed after
 * they exceeded their time to life. 
 * 
 * @author maumau
 *
 */
public class CartItem {
	@Id
	private String id;
	private Map<String, Integer> content;
	
	private Date creationDate; 
	
	public CartItem(String id, Map<String, Integer> content, Date creationDate) {
		super();
		this.id = id;
		this.content = content;
		this.creationDate = creationDate;
	}
	
	public CartItem(String id, Map<String, Integer> content) {
		this(id, content, Date.from(Instant.now()));
	}
	
	public CartItem(String id) {
		this(id, new HashMap<>(), Date.from(Instant.now()));
	}
	
	public CartItem() {
		this(null, new HashMap<>(), Date.from(Instant.now()));
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

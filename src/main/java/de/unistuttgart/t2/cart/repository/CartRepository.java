package de.unistuttgart.t2.cart.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import de.unistuttgart.t2.cart.domain.CartItem;

@RepositoryRestResource(
	    path = "cart",
	    itemResourceRel = "cart",
	    collectionResourceRel = "cart"
	)
public interface CartRepository extends MongoRepository<CartItem, String>{

}

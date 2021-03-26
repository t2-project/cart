package de.unistuttgart.t2.cart.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import de.unistuttgart.t2.cart.domain.CartEntry;

@RepositoryRestResource(
	    path = "gen-cart",
	    itemResourceRel = "gen-cart",
	    collectionResourceRel = "gen-cart"
	)
public interface CartRepository extends MongoRepository<CartEntry, String>{

}

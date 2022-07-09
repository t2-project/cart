package de.unistuttgart.t2.cart.repository;

import java.util.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "cart", itemResourceRel = "cart", collectionResourceRel = "cart")
public interface CartRepository extends MongoRepository<CartItem, String> {

    void deleteByIdIn(Collection<String> ids);
}

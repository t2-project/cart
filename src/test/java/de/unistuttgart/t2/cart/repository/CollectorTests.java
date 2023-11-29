package de.unistuttgart.t2.cart.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests that the collector for expired carts works as expected.
 *
 * @author maumau
 */
@DataMongoTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestContext.class)
@ActiveProfiles("test")
class CollectorTests {

    @Autowired
    TimeoutCollector collector;

    @Autowired
    CartRepository repository;

    @BeforeEach
    public void populateRepository() {
        repository.deleteAll();
        for (int i = 0; i < 5; i++) {
            repository.save(new CartItem());
        }
    }

    @Test
    public void collectAllEntriesTest() {
        collector.cleanup();
        assertEquals(0, repository.count());
    }

    @Test
    public void collectSomeEntriesTest() {
        CartItem item = new CartItem();
        item.setCreationDate(Date.from(Instant.now().plusSeconds(60)));
        repository.save(item);

        collector.cleanup();
        assertEquals(1, repository.count());
    }
}

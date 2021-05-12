package de.unistuttgart.t2.cart.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
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
 * actually i was trying to test my collector.. but timing is a bad bitch and it
 * fucks me up.
 * 
 * @author maumau
 *
 */
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
    public void collectAllEntriesTest() throws InterruptedException {
        
        // WFT o_O
        TimeoutCollector.CartDeletionTask task = collector.new CartDeletionTask();

        task.run();

        assertEquals(0, repository.count());
    }
    
    @Test
    public void collectSomeEntriesTest() throws InterruptedException {
        CartItem item = new CartItem();
        item.setCreationDate(Date.from(Instant.now().plusSeconds(60)));
        repository.save(item);
        
        // WFT o_O
        TimeoutCollector.CartDeletionTask task = collector.new CartDeletionTask();

        task.run();

        assertEquals(1, repository.count());
    }
}

package de.unistuttgart.t2.cart.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.Date;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestContext.class)
@SpringBootTest
@ActiveProfiles("test")
/**
 * Actually, I was trying to test my collector.<br>
 * But timing is a bad bitch and it fucks me up.
 *
 * @author maumau
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
        collector.cleanup.run();
        assertEquals(0, repository.count());
    }

    @Test
    public void collectSomeEntriesTest() throws InterruptedException {
        CartItem item = new CartItem();
        item.setCreationDate(Date.from(Instant.now().plusSeconds(60)));
        repository.save(item);

        collector.cleanup.run();
        assertEquals(1, repository.count());
    }
}

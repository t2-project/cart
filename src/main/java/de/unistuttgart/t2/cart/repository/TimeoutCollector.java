package de.unistuttgart.t2.cart.repository;

import java.time.Instant;
import java.util.*;

import javax.annotation.PostConstruct;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Periodically checks all reservations and deletes those whose time to life has been exceeded.
 * <p>
 * (apparently there is a mongo native attach on expiry date to documents, but i didn't find anything on whether this
 * also works with the spring repository interface. thus the manual deletion.)
 *
 * @author maumau
 */
@Component
public class TimeoutCollector {

    private final long TTL; // seconds
    private final int taskRate; // milliseconds

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private CartRepository repository;

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    /**
     * Create collector.
     *
     * @param TTL      the cart entries' time to live in seconds
     * @param taskRate rate at which the collector checks the repo in milliseconds
     */
    @Autowired
    public TimeoutCollector(@Value("${t2.cart.TTL:0}") long TTL, @Value("${t2.cart.taskRate:0}") int taskRate) {
        this.TTL = TTL;
        this.taskRate = taskRate;
    }

    /**
     * Schedule the task to check cart contents and delete them if necessary.
     * <p>
     * If the taskRate is 0, no task will be scheduled.
     */
    @PostConstruct
    public void scheduleTask() {
        if (taskRate > 0) {
            taskScheduler.scheduleAtFixedRate(new CartDeletionTask(), taskRate);
        }
    }

    /**
     * The Task that does the actual checking and deleting.
     *
     * @author maumau
     */
    protected class CartDeletionTask implements Runnable {

        @Override
        public void run() {
            List<String> expiredItems = getExpiredCarts();
            for (String sessionId : expiredItems) {
                deleteItem(sessionId);
            }
            LOG.info(String.format("deleted %d expired items", expiredItems.size()));
        }

        /**
         * Get all ids of expired carts.
         * <p>
         * The get step is separated from the delete step because i want to lock the db as little as possible and need
         * not do it for getting the ids.
         * <p>
         * Carts that were created earlier than {@code TTL} seconds before 'now' are expired.
         *
         * @return
         */
        private List<String> getExpiredCarts() {
            List<String> rval = new ArrayList<>();
            List<CartItem> items = repository.findAll();
            Date threshold = Date.from(Instant.now().minusSeconds(TTL));
            for (CartItem item : items) {
                if (item.getCreationDate().before(threshold)) {
                    rval.add(item.getId());
                }
            }
            return rval;
        }

        /**
         * Delete cart from repository.
         *
         * @param sessionId to identify the cart to be deleted.
         */
        @Transactional
        private void deleteItem(String sessionId) {
            repository.deleteById(sessionId);
        }
    }
}

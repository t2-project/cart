package de.unistuttgart.t2.cart.repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Periodically checks all reservations and deletes those whose time to life has been exceeded.
 * 
 * <p>
 * (apparently there is a mongo native attach on expiry date to documents, but i didn't find anything on
 * whether this also works with the spring repository interface. thus the manual deletion.)
 * 
 * @author maumau
 *
 */
@Component
public class TimeoutCollector {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Value("${t2.cart.TTL:0}") // in seconds
	protected long TTL;
	@Value("${t2.cart.taskRate:0}") // in milliseconds
	protected int taskRate;

	@Autowired
	CartRepository repository;

	@Autowired
	private ThreadPoolTaskScheduler taskScheduler;

	/**
     * Schedule the task to check cart contents and delete them if necessary.
     * 
     * <p>
     * If either the TTL or the taskRate is 0, no task will be scheduled.
     */
	@PostConstruct
	public void schedulePeriodically() {
		//disable
		if (taskRate > 0 && TTL > 0) {
			taskScheduler.scheduleAtFixedRate(new CartDeletionTask(), taskRate);
		}
	}

	/**
	 * The Task that does the actual checking and deleting.
	 * 
	 * @author maumau
	 *
	 */
	class CartDeletionTask implements Runnable {

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
		 * 
		 * <p>
		 * The get step is separated from the delete step because i want to lock the db as little as possible and need not do it for getting the ids.
		 * 
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

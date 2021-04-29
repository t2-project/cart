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
 * responsible for deleting cart items that exceeded their time to live.
 * 
 * actually you can mongo native attach an expiry date to documents, but i don't
 * how the repository interface works (and whether each item is its own document)
 * thus manual deletion.
 * 
 * @author maumau
 *
 */
@Component
public class TimeoutCollector {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Value("${TTL:2}") // in seconds
	protected long TTL;
	@Value("${taskRate:2000}") // in milliseconds
	protected int taskRate;

	@Autowired
	CartRepository repository;

	@Autowired
	private ThreadPoolTaskScheduler taskScheduler;

	@PostConstruct
	public void schedulePeriodically() {
		//disable
		if (taskRate > 0 && TTL > 0) {
			taskScheduler.scheduleAtFixedRate(new RunnableTask(), taskRate);
		}
	}

	/**
	 * executed periodically, checks the repository and deletes entities whose ttl expired.
	 * 
	 * @author maumau
	 *
	 */
	class RunnableTask implements Runnable {

		@Override
		public void run() {
			List<String> expiredItems = getExpiredItems();
			for (String id : expiredItems) {
				deleteItem(id);
			}
			LOG.info(String.format("delete %d expired items", expiredItems.size()));
		}

		/**
		 * get all ids of all expired items.
		 * 
		 * get first and delete later such that the db is locked as little as possible.
		 * all items that were created earlier than TTL seconds before 'now' are expired.
		 * 
		 * @return
		 */
		private List<String> getExpiredItems() {
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
		 * delete item from db.
		 * 
		 * this is a stand alone method because i do not know how save delete is, but i
		 * know that annotating a method with 'transactional' makes it save.
		 * 
		 * @param id of item to be deleted.
		 */
		@Transactional
		private void deleteItem(String id) {
			repository.deleteById(id);
		}
	}
}

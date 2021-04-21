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
 * periodically checks the repository and deletes entities whose ttl expired.
 * 
 * actually you can mongo native attach an expiry date to documents, but i don't
 * how the repository interface works (and wether each item is its own document)
 * thus manual deletion.
 * 
 * @author maumau
 *
 */
@Component
public class TimeoutCollector {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Value("${TTL:20}") // in seconds
	long TTL;
	@Value("${taskRate:20000}") // in milliseconds
	int taskRate;

	@Autowired
	CartRepository repository;

	@Autowired
	private ThreadPoolTaskScheduler taskScheduler;

	@PostConstruct
	public void schedulePeriodically() {
		taskScheduler.scheduleAtFixedRate(new RunnableTask(), taskRate);
	}

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
		 * get first and delete later because i want to lock db as little as possible.
		 * 
		 * @return
		 */
		private List<String> getExpiredItems() {
			List<String> rval = new ArrayList<>();
			List<CartItem> items = repository.findAll();
			Date now = Date.from(Instant.now().plusSeconds(TTL));
			for (CartItem item : items) {
				// check creation date vs. TTL
				if (item.getCreationDate().before(now)) { // fix duration, but that's the gist
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

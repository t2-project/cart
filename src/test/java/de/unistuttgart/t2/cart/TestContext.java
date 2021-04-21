package de.unistuttgart.t2.cart;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import de.unistuttgart.t2.cart.repository.CartRepository;
import de.unistuttgart.t2.cart.repository.TTLCollector;

@Configuration
@EnableAutoConfiguration
@EnableMongoRepositories(basePackageClasses = {CartRepository.class})
@Profile("test")
public class TestContext {
    @Bean 
    public TTLCollector collector() {
    	return new TTLCollector();
    }
    
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(5);
        threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
        return threadPoolTaskScheduler;
    }
}

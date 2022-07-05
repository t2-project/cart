package de.unistuttgart.t2.cart.repository;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import io.opentracing.contrib.java.spring.jaeger.starter.JaegerAutoConfiguration;
import io.opentracing.contrib.spring.web.starter.ServerTracingAutoConfiguration;

@Configuration
@EnableAutoConfiguration(exclude = { ServerTracingAutoConfiguration.class, JaegerAutoConfiguration.class })
@EnableMongoRepositories(basePackageClasses = { CartRepository.class })
@Profile("test")
public class TestContext {

    @Bean
    public TimeoutCollector timeoutCollector() {
        return new TimeoutCollector(0, 0);
    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(5);
        threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
        return threadPoolTaskScheduler;
    }
}

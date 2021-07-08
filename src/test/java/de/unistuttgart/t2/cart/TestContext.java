package de.unistuttgart.t2.cart;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import de.unistuttgart.t2.cart.repository.CartRepository;
import io.opentracing.contrib.java.spring.jaeger.starter.JaegerAutoConfiguration;
import io.opentracing.contrib.spring.web.starter.ServerTracingAutoConfiguration;

@Configuration
@EnableAutoConfiguration(exclude = {ServerTracingAutoConfiguration.class, JaegerAutoConfiguration.class})
@EnableMongoRepositories(basePackageClasses = {CartRepository.class})
@Profile("test")
public class TestContext {
}

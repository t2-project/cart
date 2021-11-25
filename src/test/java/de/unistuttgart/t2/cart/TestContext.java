package de.unistuttgart.t2.cart;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.sleuth.autoconfig.zipkin2.ZipkinAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import de.unistuttgart.t2.cart.repository.CartRepository;

@Configuration
@EnableAutoConfiguration(exclude = {ZipkinAutoConfiguration.class})
@EnableMongoRepositories(basePackageClasses = {CartRepository.class})
@Profile("test")
public class TestContext {}

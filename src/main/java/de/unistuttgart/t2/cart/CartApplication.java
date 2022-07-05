package de.unistuttgart.t2.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import de.unistuttgart.t2.cart.repository.CartRepository;
import de.unistuttgart.t2.common.BaseScan;
import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;

/**
 * Manages the products in the users carts.
 * <p>
 * Users are distinguished by their session's ids.
 * <p>
 * Neither controller nor Service because all endpoints are generated.
 *
 * @author maumau
 */
@SpringBootApplication(scanBasePackageClasses = BaseScan.class)
@EnableMongoRepositories(basePackageClasses = CartRepository.class)
public class CartApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class, args);
    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(5);
        threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
        return threadPoolTaskScheduler;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .components(new Components())
            .info(new Info().title("Cart service API").description(
                "API of the T2 Store's cart service."));
    }
}

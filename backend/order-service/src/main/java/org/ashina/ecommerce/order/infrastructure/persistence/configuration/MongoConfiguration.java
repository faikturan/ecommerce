package org.ashina.ecommerce.order.infrastructure.persistence.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.time.LocalDateTime;
import java.util.Optional;

@Configuration
@EnableMongoRepositories(basePackages = "org.ashina.ecommerce.order.infrastructure.persistence.repository")
@EnableMongoAuditing(dateTimeProviderRef = "dateTimeProvider")
public class MongoConfiguration {

    @Bean
    public DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(LocalDateTime.now());
    }
}

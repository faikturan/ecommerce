package org.ashina.ecommerce.faker.configuration.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
@EnableConfigurationProperties(MultipleMongoProperties.class)
@RequiredArgsConstructor
public class MultipleMongoConfiguration {

    private final MultipleMongoProperties properties;

    @Bean
    @Primary
    public MongoDatabaseFactory customerMongoDbFactory(final String url) {
        return new SimpleMongoClientDatabaseFactory(url);
    }

    @Bean
    @Primary
    public MongoTemplate customerMongoTemplate() {
        return new MongoTemplate(customerMongoDbFactory(properties.getCustomer()));
    }

    @Bean
    public MongoDatabaseFactory productMongoDbFactory(final String url) {
        return new SimpleMongoClientDatabaseFactory(url);
    }

    @Bean
    public MongoTemplate productMongoTemplate() {
        return new MongoTemplate(productMongoDbFactory(properties.getProduct()));
    }

    @Bean
    public MongoDatabaseFactory orderMongoDbFactory(final String url) {
        return new SimpleMongoClientDatabaseFactory(url);
    }

    @Bean
    public MongoTemplate orderMongoTemplate() {
        return new MongoTemplate(orderMongoDbFactory(properties.getOrder()));
    }
}

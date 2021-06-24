package org.ashina.ecommerce.faker.configuration.mongo;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
        basePackages = "org.ashina.ecommerce.faker.repository.product",
        mongoTemplateRef = "productMongoTemplate"
)
public class ProductRepositoryConfiguration {
}

package org.ashina.ecommerce.faker.configuration.mongo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "mongo")
public class MultipleMongoProperties {

    private String customer;
    private String product;
    private String order;
}

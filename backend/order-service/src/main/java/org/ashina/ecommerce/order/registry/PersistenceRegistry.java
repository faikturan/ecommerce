package org.ashina.ecommerce.order.registry;

import org.ashina.ecommerce.order.infrastructure.persistence.OrderPersistence;
import org.ashina.ecommerce.order.infrastructure.persistence.jpa.adapter.JpaOrderPersistence;
import org.ashina.ecommerce.order.infrastructure.persistence.jpa.converter.JpaOrderConverter;
import org.ashina.ecommerce.order.infrastructure.persistence.jpa.converter.JpaOrderLineConverter;
import org.ashina.ecommerce.order.infrastructure.persistence.jpa.repository.JpaOrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceRegistry {

    @Bean
    public JpaCartLineConverter jpaCartLineConverter() {
        return new JpaCartLineConverter();
    }

    @Bean
    public CartLinePersistence cartLinePersistence(JpaCartLineRepository jpaCartLineRepository,
                                                   JpaCartLineConverter jpaCartLineConverter) {
        return new JpaCartLinePersistence(jpaCartLineRepository, jpaCartLineConverter);
    }

    @Bean
    public JpaOrderLineConverter jpaOrderLineConverter() {
        return new JpaOrderLineConverter();
    }

    @Bean
    public JpaOrderConverter jpaOrderConverter(JpaOrderLineConverter jpaOrderLineConverter) {
        return new JpaOrderConverter(jpaOrderLineConverter);
    }

    @Bean
    public OrderPersistence orderPersistence(JpaOrderRepository jpaOrderRepository,
                                             JpaOrderConverter jpaOrderConverter) {
        return new JpaOrderPersistence(jpaOrderRepository, jpaOrderConverter);
    }
}

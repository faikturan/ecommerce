package org.ashina.ecommerce.cart.registry;

import org.ashina.ecommerce.cart.infrastructure.persistence.CartLinePersistence;
import org.ashina.ecommerce.cart.infrastructure.persistence.jpa.adapter.JpaCartLinePersistence;
import org.ashina.ecommerce.cart.infrastructure.persistence.jpa.converter.JpaCartLineConverter;
import org.ashina.ecommerce.cart.infrastructure.persistence.jpa.repository.JpaCartLineRepository;
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
}

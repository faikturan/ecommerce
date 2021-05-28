package org.ashina.ecommerce.cart.infrastructure.persistence;

import org.ashina.ecommerce.cart.domain.CartLine;

import java.util.List;
import java.util.Optional;

public interface CartLinePersistence {

    List<CartLine> findByCustomerId(String customerId);

    Optional<CartLine> findByCustomerIdAndProductId(String customerId, String productId);

    void save(CartLine cartLine);

    void delete(CartLine cartLine);
}

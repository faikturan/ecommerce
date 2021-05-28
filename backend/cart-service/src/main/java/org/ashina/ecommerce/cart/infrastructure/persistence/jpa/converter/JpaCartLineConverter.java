package org.ashina.ecommerce.cart.infrastructure.persistence.jpa.converter;

import org.ashina.ecommerce.cart.domain.CartLine;
import org.ashina.ecommerce.cart.infrastructure.persistence.jpa.entity.JpaCartLine;
import org.ashina.ecommerce.sharedkernel.persistence.PersistenceConverter;

public class JpaCartLineConverter implements PersistenceConverter<CartLine, JpaCartLine> {

    @Override
    public JpaCartLine mapToPersistentObject(CartLine cartLine) {
        JpaCartLine jpaCartLine = new JpaCartLine();
        jpaCartLine.setId(cartLine.getId());
        jpaCartLine.setCustomerId(cartLine.getCustomerId());
        jpaCartLine.setProductId(cartLine.getProductId());
        jpaCartLine.setQuantity(cartLine.getQuantity());
        return jpaCartLine;
    }

    @Override
    public CartLine mapToDomainEntity(JpaCartLine jpaCartLine) {
        CartLine cartLine = new CartLine(jpaCartLine.getId());
        cartLine.setCustomerId(jpaCartLine.getCustomerId());
        cartLine.setProductId(jpaCartLine.getProductId());
        cartLine.setQuantity(jpaCartLine.getQuantity());
        return cartLine;
    }
}

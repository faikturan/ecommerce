package org.ashina.ecommerce.cart.infrastructure.persistence.jpa.adapter;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.cart.domain.CartLine;
import org.ashina.ecommerce.cart.infrastructure.persistence.CartLinePersistence;
import org.ashina.ecommerce.cart.infrastructure.persistence.jpa.converter.JpaCartLineConverter;
import org.ashina.ecommerce.cart.infrastructure.persistence.jpa.entity.JpaCartLine;
import org.ashina.ecommerce.cart.infrastructure.persistence.jpa.repository.JpaCartLineRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JpaCartLinePersistence implements CartLinePersistence {

    private final JpaCartLineRepository jpaCartLineRepository;
    private final JpaCartLineConverter jpaCartLineConverter;

    @Override
    public List<CartLine> findByCustomerId(String customerId) {
        return jpaCartLineRepository.findByCustomerId(customerId)
                .stream()
                .map(jpaCartLineConverter::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CartLine> findByCustomerIdAndProductId(String customerId, String productId) {
        return jpaCartLineRepository.findByCustomerIdAndProductId(customerId, productId)
                .map(jpaCartLineConverter::mapToDomainEntity);
    }

    @Override
    public void save(CartLine cartLine) {
        JpaCartLine jpaCart = jpaCartLineConverter.mapToPersistentObject(cartLine);
        jpaCartLineRepository.save(jpaCart);
    }

    @Override
    public void delete(CartLine cartLine) {
        jpaCartLineRepository.deleteById(cartLine.getId());
    }
}

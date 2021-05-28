package org.ashina.ecommerce.customer.infrastructure.persistence.jpa.adapter;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.customer.infrastructure.persistence.AddressBookPersistence;
import org.ashina.ecommerce.customer.infrastructure.persistence.jpa.converter.JpaAddressBookConverter;
import org.ashina.ecommerce.customer.infrastructure.persistence.jpa.entity.JpaAddressBook;
import org.ashina.ecommerce.customer.infrastructure.persistence.jpa.repository.JpaAddressBookRepository;

import java.util.Optional;

@RequiredArgsConstructor
public class JpaAddressBookBookPersistence implements AddressBookPersistence {

    private final JpaAddressBookRepository jpaAddressBookRepository;
    private final JpaAddressBookConverter jpaAddressBookConverter;

    @Override
    public Optional<AddressBook> findByCustomerId(String customerId) {
        return jpaAddressBookRepository.findById(customerId)
                .map(jpaAddressBookConverter::mapToDomainEntity);
    }

    @Override
    public void save(AddressBook addressBook) {
        JpaAddressBook jpaAddressBook = jpaAddressBookConverter.mapToPersistentObject(addressBook);
        jpaAddressBookRepository.save(jpaAddressBook);
    }
}

package org.ashina.ecommerce.customer.infrastructure.persistence.jpa.converter;

import org.ashina.ecommerce.customer.infrastructure.persistence.jpa.entity.JpaAddressBook;
import org.ashina.ecommerce.sharedkernel.persistence.PersistenceConverter;

public class JpaAddressBookConverter implements PersistenceConverter<AddressBook, JpaAddressBook> {

    @Override
    public JpaAddressBook mapToPersistentObject(AddressBook addressBook) {
        JpaAddressBook jpaAddressBook = new JpaAddressBook();
        jpaAddressBook.setId(addressBook.getId());
        jpaAddressBook.setFullName(addressBook.getFullName());
        jpaAddressBook.setPhoneNumber(addressBook.getPhoneNumber());
        jpaAddressBook.setAddress(addressBook.getAddress());
        return jpaAddressBook;
    }

    @Override
    public AddressBook mapToDomainEntity(JpaAddressBook jpaAddressBook) {
        AddressBook addressBook = new AddressBook((jpaAddressBook.getId()));
        addressBook.setFullName(jpaAddressBook.getFullName());
        addressBook.setPhoneNumber(jpaAddressBook.getPhoneNumber());
        addressBook.setAddress(jpaAddressBook.getAddress());
        return addressBook;
    }
}

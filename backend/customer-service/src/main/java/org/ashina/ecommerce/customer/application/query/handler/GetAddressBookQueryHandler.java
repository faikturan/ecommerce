package org.ashina.ecommerce.customer.application.query.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.customer.application.error.ErrorCode;
import org.ashina.ecommerce.customer.application.query.model.GetAddressBookQuery;
import org.ashina.ecommerce.customer.application.query.model.GetAddressBookView;
import org.ashina.ecommerce.customer.domain.AddressBook;
import org.ashina.ecommerce.customer.infrastructure.persistence.AddressBookPersistence;
import org.ashina.ecommerce.sharedkernel.exception.DomainException;
import org.ashina.ecommerce.sharedkernel.query.handler.QueryHandler;
import org.ashina.ecommerce.sharedkernel.query.model.Query;

@RequiredArgsConstructor
public class GetAddressBookQueryHandler implements QueryHandler<GetAddressBookQuery, GetAddressBookView> {

    private final AddressBookPersistence addressBookPersistence;

    @Override
    public Class<? extends Query> support() {
        return GetAddressBookQuery.class;
    }

    @Override
    public GetAddressBookView handle(GetAddressBookQuery query) throws Exception {
        AddressBook addressBook = addressBookPersistence.findByCustomerId(query.getCustomerId())
                .orElseThrow(() -> new DomainException(
                        ErrorCode.ADDRESS_NOT_FOUND,
                        String.format("Customer %s does not have address", query.getCustomerId())
                ));
        return new GetAddressBookView(addressBook);
    }
}

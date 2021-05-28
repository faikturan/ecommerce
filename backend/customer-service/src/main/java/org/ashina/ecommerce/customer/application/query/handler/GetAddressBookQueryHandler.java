package org.ashina.ecommerce.customer.application.query.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.customer.application.error.ErrorCode;
import org.ashina.ecommerce.customer.application.error.ServiceException;
import org.ashina.ecommerce.customer.application.query.model.GetAddressBookQuery;
import org.ashina.ecommerce.customer.application.query.model.GetAddressBookView;
import org.ashina.ecommerce.customer.infrastructure.persistence.AddressBookPersistence;
import org.ashina.ecommerce.sharedkernel.query.handler.QueryHandler;
import org.ashina.ecommerce.sharedkernel.query.model.Query;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class GetAddressBookQueryHandler implements QueryHandler<GetAddressBookQuery, GetAddressBookView> {

    private final AddressBookPersistence addressBookPersistence;

    @Override
    public Class<? extends Query> support() {
        return GetAddressBookQuery.class;
    }

    @Override
    public GetAddressBookView handle(GetAddressBookQuery query) {
        AddressBook addressBook = addressBookPersistence.findByCustomerId(query.getCustomerId())
                .orElseThrow(() -> new ServiceException(
                        ErrorCode.ADDRESS_NOT_FOUND,
                        String.format("Customer %s does not have address", query.getCustomerId()),
                        HttpStatus.NOT_FOUND
                ));
        return new GetAddressBookView(addressBook);
    }
}

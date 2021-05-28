package org.ashina.ecommerce.customer.application.query.model;

import lombok.Data;
import org.ashina.ecommerce.sharedkernel.query.model.View;

@Data
public class GetAddressBookView extends View {

    private String fullName;
    private String phoneNumber;
    private String address;

    public GetAddressBookView(AddressBook addressBook) {
        this.fullName = addressBook.getFullName();
        this.phoneNumber = addressBook.getPhoneNumber();
        this.address = addressBook.getAddress();
    }
}

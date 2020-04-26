package com.prokarma.retail.customer.service.producer.service.helper;

import static com.prokarma.retail.customer.service.producer.constants.ProducerServiceConstants.*;
import org.springframework.stereotype.Component;
import com.prokarma.retail.customer.service.producer.model.Address;
import com.prokarma.retail.customer.service.producer.model.Customer;

@Component
public class MaskHelper {



  public Customer maskCustomer(Customer source) {
    if (source == null) {
      return null;
    }

    Customer customer = new Customer();

    customer.setCustomerNumber(maskCustomerNumber(source.getCustomerNumber()));
    customer.setFirstName(source.getFirstName());
    customer.setLastName(source.getLastName());
    customer.setBirthDate(source.getBirthDate());
    customer.setCountry(source.getCountry());
    customer.setCountryCode(source.getCountryCode());
    customer.setMobileNumber(source.getMobileNumber());
    customer.setEmail(maskEmail(source.getEmail()));
    customer.setCustomerStatus(source.getCustomerStatus());
    customer.setAddress(addressToAddress(source.getAddress()));

    return customer;
  }

  private String maskCustomerNumber(String customerNumber) {
    return customerNumber.replaceAll(CUSTOMER_NUMBER_MASK_REGEX, CUSTOMER_NUMBER_MASK_REPLACE_TEXT);
  }

  private String maskEmail(String email) {
    return email.replaceAll(EMAIL_MASK_REGEX, EMAIL_MASK_REPLACE_TEXT);
  }

  protected Address addressToAddress(Address address) {
    if (address == null) {
      return null;
    }

    Address address1 = new Address();

    address1.setAddressLine1(address.getAddressLine1());
    address1.setAddressLine2(address.getAddressLine2());
    address1.setStreet(address.getStreet());
    address1.setPostalCode(address.getPostalCode());

    return address1;
  }
}


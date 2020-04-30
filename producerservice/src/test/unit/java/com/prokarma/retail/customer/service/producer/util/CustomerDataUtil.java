package com.prokarma.retail.customer.service.producer.util;

import java.math.BigInteger;
import java.time.LocalDate;
import com.prokarma.retail.customer.service.producer.model.Address;
import com.prokarma.retail.customer.service.producer.model.Customer;
import com.prokarma.retail.customer.service.producer.model.Customer.CustomerStatusEnum;

public class CustomerDataUtil {
  
  public static Customer prepareCustomer() {
    Customer customer = new Customer();
    customer.setCustomerNumber("C00000987654321");
    customer.setFirstName("customer first name");
    customer.setLastName("customer last name");
    customer.setBirthDate(LocalDate.of(1999, 05, 01));
    customer.setCountry("India");
    customer.setCountryCode("IN");
    customer.setMobileNumber(BigInteger.valueOf(24459239487L));
    customer.setEmail("customer@gmail.com");
    customer.setCustomerStatus(CustomerStatusEnum.O);

    Address address = new Address();

    address.setAddressLine1("AddressLine1");
    address.setAddressLine2("AddressLine2");
    address.setStreet("Street");
    address.setPostalCode("32904823");
    customer.setAddress(address);

    return customer;

  }


}

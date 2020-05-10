package com.prokarma.retail.customer.service.producer.service.helper;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import com.prokarma.retail.customer.service.producer.model.Address;
import com.prokarma.retail.customer.service.producer.model.Customer;

@RunWith(MockitoJUnitRunner.class)
public class MaskHelperTest {

  private MaskHelper maskHelper = new MaskHelper();

  @Test
  public void testCustomerMasking() {
    Customer customer = new Customer();
    customer.setCustomerNumber("C00001000100012345");
    customer.setEmail("customer@gmail.com");
    Address address = new Address();
    customer.setAddress(address);
    Customer maskCustomer = maskHelper.maskCustomer(customer);
    assertEquals("C0000100010001****", maskCustomer.getCustomerNumber());
    assertEquals("****omer@gmail.com", maskCustomer.getEmail());

  }

  @Test
  public void testCustomerMaskingWithCustomerAsNull() {

    Customer maskCustomer = maskHelper.maskCustomer(null);
    assertEquals(null, null);
  }

  @Test
  public void testCustomerMaskingWithAddressAsNull() {
    Customer customer = new Customer();
    customer.setCustomerNumber("C00001000100012345");
    customer.setEmail("customer@gmail.com");
    customer.setAddress(null);
    Customer maskCustomer = maskHelper.maskCustomer(customer);
    assertEquals("C0000100010001****", maskCustomer.getCustomerNumber());
    assertEquals("****omer@gmail.com", maskCustomer.getEmail());

  }
}


package com.prokarma.retail.customer.service.producer.service;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.concurrent.ExecutionException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prokarma.retail.customer.service.producer.model.Address;
import com.prokarma.retail.customer.service.producer.model.Customer;
import com.prokarma.retail.customer.service.producer.model.Customer.CustomerStatusEnum;
import com.prokarma.retail.customer.service.producer.service.helper.MaskHelper;


@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {
  @Spy
  private ObjectMapper jsonMapper = new ObjectMapper();
  private MaskHelper maskHelper = new MaskHelper();

  @Mock
  private KafkaTemplate<String, String> kafkaTemplate;

  private CustomerService customerService;

  @Before
  public void setup() {
    customerService = new CustomerService(kafkaTemplate, jsonMapper, maskHelper);
  }

  @Test
  public void testPublishToKafka()
      throws JsonProcessingException, InterruptedException, ExecutionException {
    when(jsonMapper.writeValueAsString(Mockito.any(Customer.class))).thenCallRealMethod();
    when(kafkaTemplate.send(Mockito.anyString(), Mockito.anyString()))
        .thenReturn(new AsyncResult<SendResult<String, String>>(null));
    customerService.publishToKafka(prepareCustomer(), "12345678", "app234567");
    verify(kafkaTemplate, atLeast(1)).send(Mockito.anyString(), Mockito.anyString());

  }

  @Test(expected = ExecutionException.class)
  public void testPublishToKafkaResultingInExecutionException()
      throws JsonProcessingException, InterruptedException, ExecutionException {
    when(jsonMapper.writeValueAsString(Mockito.any(Customer.class))).thenCallRealMethod();
    ListenableFuture<SendResult<String, String>> forExecutionException =
        AsyncResult.forExecutionException(new ExecutionException(new InterruptedException()));
    when(kafkaTemplate.send(Mockito.anyString(), Mockito.anyString()))
        .thenReturn(forExecutionException);
    customerService.publishToKafka(prepareCustomer(), "12345678", "app234567");

  }


  @Test(expected = Exception.class)
  public void testPublishToKafkaResultingInException()
      throws JsonProcessingException, InterruptedException, ExecutionException {
    when(jsonMapper.writeValueAsString(Mockito.any(Customer.class))).thenCallRealMethod();
    when(kafkaTemplate.send(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
    customerService.publishToKafka(prepareCustomer(), "12345678", "app234567");

  }

  @SuppressWarnings("deprecation")
  @Test(expected = JsonProcessingException.class)
  public void testPublishToKafkaResultingInJsonProcessingException()
      throws JsonProcessingException, InterruptedException, ExecutionException {
    when(jsonMapper.writeValueAsString(Mockito.any(Customer.class)))
        .thenThrow(new JsonParseException("unable to parse customer",
            new JsonLocation(new Object(), 234L, 234L, 123, 123)));
    customerService.publishToKafka(prepareCustomer(), "12345678", "app234567");

  }



  private Customer prepareCustomer() {
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

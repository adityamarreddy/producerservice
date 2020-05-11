package com.prokarma.retail.customer.service.producer.controller;

import static org.junit.Assert.assertEquals;
import java.util.concurrent.ExecutionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.prokarma.retail.customer.service.producer.exception.ProducerServiceException;
import com.prokarma.retail.customer.service.producer.model.Response;
import com.prokarma.retail.customer.service.producer.model.Response.StatusEnum;
import com.prokarma.retail.customer.service.producer.service.CustomerService;

@RunWith(MockitoJUnitRunner.class)
public class ProducerServiceControllerTest {

  @Mock
  private CustomerService customerService;

  @InjectMocks
  private ProducerServiceController producerServiceController;


  @Test
  public void test() throws ProducerServiceException {
    ResponseEntity<Response> response =
        producerServiceController.addCustomer(null, "12344t345yuty", "app12345566");
    assertEquals(StatusEnum.SUCCESS, response.getBody().getStatus());
  }

}

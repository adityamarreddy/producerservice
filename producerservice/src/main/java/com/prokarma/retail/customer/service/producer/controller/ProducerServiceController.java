package com.prokarma.retail.customer.service.producer.controller;

import java.util.concurrent.ExecutionException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.prokarma.retail.customer.service.producer.exception.ProducerServiceException;
import com.prokarma.retail.customer.service.producer.model.Customer;
import com.prokarma.retail.customer.service.producer.model.Response;
import com.prokarma.retail.customer.service.producer.model.Response.StatusEnum;
import com.prokarma.retail.customer.service.producer.service.CustomerService;

@RestController
@RequestMapping(value = "/customer")
public class ProducerServiceController implements CustomerApi {

  @Autowired
  private CustomerService service;


  @Override
  public ResponseEntity<Response> addCustomer(@Valid @RequestBody Customer customer,
      @RequestHeader(value = "Activity-Id", required = true) String activityId,
      @RequestHeader(value = "Application-Id", required = true) String applicationId)
      throws ProducerServiceException {

    service.publishToKafka(customer, activityId, applicationId);
    return new ResponseEntity<>(
        new Response().message("Successfully created customer").status(StatusEnum.SUCCESS),
        HttpStatus.OK);
  }

}

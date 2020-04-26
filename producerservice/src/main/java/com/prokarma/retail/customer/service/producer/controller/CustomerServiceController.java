package com.prokarma.retail.customer.service.producer.controller;

import java.util.concurrent.ExecutionException;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.prokarma.retail.customer.service.producer.exception.CustomerServiceException;
import com.prokarma.retail.customer.service.producer.model.Customer;
import com.prokarma.retail.customer.service.producer.model.Response;
import com.prokarma.retail.customer.service.producer.model.Response.StatusEnum;
import com.prokarma.retail.customer.service.producer.service.CustomerService;

@RestController
@RequestMapping(value = "/customer")
public class CustomerServiceController implements CustomerApi {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceController.class);
  @Autowired
  private CustomerService service;


  @Override
  public ResponseEntity<Response> addCustomer(@Valid @RequestBody Customer customer,
      @RequestHeader(value = "Activity-Id", required = true) String activityId,
      @RequestHeader(value = "Application-Id", required = true) String applicationId)
      throws InterruptedException, ExecutionException, CustomerServiceException {

    // LOGGER.info(String.format("Received reqeust from Application id=%s with ActivityId=%s",
    // applicationId, activityId));
    service.publishToKafka(customer);

    return new ResponseEntity<Response>(
        new Response().message("Successfully created customer").status(StatusEnum.SUCCESS),
        HttpStatus.OK);
  }

}

/*
 * } catch (CustomerServiceException e) { return new ResponseEntity<Response>(new
 * Response().message("Customer creation is failed")
 * .status(StatusEnum.ERROR).errorType(e.getClass().getName()), HttpStatus.INTERNAL_SERVER_ERROR); }
 */

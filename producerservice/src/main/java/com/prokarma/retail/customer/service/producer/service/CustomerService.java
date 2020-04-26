package com.prokarma.retail.customer.service.producer.service;

import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.prokarma.retail.customer.service.producer.exception.CustomerServiceException;
import com.prokarma.retail.customer.service.producer.model.Customer;
import com.prokarma.retail.customer.service.producer.util.MaskingUtil;

@Service
public class CustomerService {
  @Autowired
  private ObjectMapper mapper;

  private KafkaTemplate<String, String> kafkaTemplate;
  private Gson jsonConverter;
  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

  @Autowired
  public CustomerService(KafkaTemplate<String, String> kafkaTemplate, Gson jsonConverter) {
    this.kafkaTemplate = kafkaTemplate;
    this.jsonConverter = jsonConverter;
  }

  public void publishToKafka(Customer customer) throws CustomerServiceException, InterruptedException, ExecutionException {
    try {
      System.out.println(mapper.writeValueAsString(customer));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    ListenableFuture<SendResult<String, String>> future =
        kafkaTemplate.send("customerTopic", jsonConverter.toJson(customer));
    SendResult<String, String> sendResult;
    try {
      sendResult = future.get();
      sendResult.getProducerRecord().headers();
    } catch (InterruptedException ex) {
      LOGGER.error(String.format("failed to publish record to kafka:: %s",
          jsonConverter.toJson(masked(customer))), ex);
      throw ex;
    } catch (ExecutionException ex) {
      LOGGER.error(String.format("failed to publish record to kafka:: %s",
          jsonConverter.toJson(masked(customer))), ex);
      throw ex;
    }

  }

  private Customer masked(Customer customer) {
    try {
      customer.setCustomerNumber(MaskingUtil.maskString(customer.getCustomerNumber(),
          customer.getCustomerNumber().length() - 4, customer.getCustomerNumber().length(), '*'));
      customer.setEmail(MaskingUtil.maskEmailAddress(customer.getEmail(), '*'));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return customer;
  }
}

/*
 * future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
 * 
 * @Override public void onSuccess(SendResult<String, String> result) {
 * LOGGER.info(String.format("record successfully published to kafka:: %s ",
 * jsonConverter.toJson(masked(customer)))); }
 * 
 * @Override public void onFailure(Throwable ex) {
 * LOGGER.error(String.format("failed to publish record to kafka:: %s",
 * jsonConverter.toJson(masked(customer))), ex); }
 * 
 * });
 * 
 */

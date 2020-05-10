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
import com.prokarma.retail.customer.service.producer.model.Customer;
import com.prokarma.retail.customer.service.producer.service.helper.MaskHelper;

@Service
public class CustomerService {

  private ObjectMapper jsonMapper;
  private MaskHelper maskHelper;
  private KafkaTemplate<String, String> kafkaTemplate;
  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

  @Autowired
  public CustomerService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper jsonMapper,
      MaskHelper maskHelper) {
    this.kafkaTemplate = kafkaTemplate;
    this.jsonMapper = jsonMapper;
    this.maskHelper = maskHelper;
  }

  public void publishToKafka(Customer customer, String activityId, String applicationId)
      throws InterruptedException, ExecutionException, JsonProcessingException {

    try {
      if (LOGGER.isInfoEnabled()) {
        LOGGER.info(String.format(
            "Kafka publish:: activityId=%s and applicationId=%s and message %s", activityId,
            applicationId, jsonMapper.writeValueAsString(maskHelper.maskCustomer(customer))));
      }
      ListenableFuture<SendResult<String, String>> future =
          kafkaTemplate.send("customerTopic", jsonMapper.writeValueAsString(customer));
      future.get();
    } catch (ExecutionException ex) {
      LOGGER.error(String.format(
          "failed to publish record to kafka:::: activityId=%s and applicationId=%s and message %s",
          activityId, applicationId,
          jsonMapper.writeValueAsString(maskHelper.maskCustomer(customer))), ex);
      throw ex;
    } catch (JsonProcessingException ex) {
      LOGGER.error(
          String.format("Unable to convert customer to json :: activityId=%s and applicationId=%s",
              activityId, applicationId),
          ex);
      throw ex;
    } catch (Exception ex) {
      LOGGER.error(String.format(
          "failed to publish record to kafka:: :: activityId=%s and applicationId=%s and message %s",
          activityId, applicationId,
          jsonMapper.writeValueAsString(maskHelper.maskCustomer(customer))), ex);
      throw ex;
    }

  }


}

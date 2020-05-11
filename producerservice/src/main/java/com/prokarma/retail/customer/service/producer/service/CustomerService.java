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
import com.prokarma.retail.customer.service.producer.exception.ProducerServiceException;
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
      throws ProducerServiceException {

    try {
      if (LOGGER.isInfoEnabled()) {
        LOGGER
            .info(String.format("Kafka publish:: activityId=%s and applicationId=%s and message %s",
                activityId, applicationId,
                writeAsJson(maskHelper.maskCustomer(customer), activityId, applicationId)));
      }
      ListenableFuture<SendResult<String, String>> future =
          kafkaTemplate.send("customerTopic", writeAsJson(customer, activityId, applicationId));
      future.get();
    } catch (ExecutionException ex) {
      LOGGER.error(String.format(
          "failed to publish record to kafka:::: activityId=%s and applicationId=%s and message %s",
          activityId, applicationId,
          writeAsJson(maskHelper.maskCustomer(customer), activityId, applicationId)), ex);
      throw new ProducerServiceException(ex);
    } catch (InterruptedException ex) {
      LOGGER.error(String.format(
          "failed to publish record to kafka:: :: activityId=%s and applicationId=%s and message %s",
          activityId, applicationId,
          writeAsJson(maskHelper.maskCustomer(customer), activityId, applicationId), ex));
      throw new ProducerServiceException(ex);
    }

  }

  private String writeAsJson(Customer customer, String activityId, String applicationId)
      throws ProducerServiceException {

    try {
      return jsonMapper.writeValueAsString(customer);
    } catch (JsonProcessingException e) {
      LOGGER.error(
          String.format("Unable to convert customer to json :: activityId=%s and applicationId=%s",
              activityId, applicationId),
          e);
      throw new ProducerServiceException(e);
    }

  }


}

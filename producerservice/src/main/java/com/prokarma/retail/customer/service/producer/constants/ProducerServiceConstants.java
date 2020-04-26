package com.prokarma.retail.customer.service.producer.constants;

public class ProducerServiceConstants {


  private ProducerServiceConstants() {
    //private constructor
  }

  public static final String CUSTOMER_NUMBER_MASK_REGEX = ".{4}$";
  public static final String CUSTOMER_NUMBER_MASK_REPLACE_TEXT = "****";
  public static final String EMAIL_MASK_REGEX = "^\\w{4}";
  public static final String EMAIL_MASK_REPLACE_TEXT = "****";
}

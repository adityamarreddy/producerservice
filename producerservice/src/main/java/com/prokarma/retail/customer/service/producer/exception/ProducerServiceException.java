package com.prokarma.retail.customer.service.producer.exception;

public class ProducerServiceException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public ProducerServiceException() {
    super();
  }

  public ProducerServiceException(String s) {
    super(s);
  }

  public ProducerServiceException(Exception ex) {
    super(ex);
  }

}

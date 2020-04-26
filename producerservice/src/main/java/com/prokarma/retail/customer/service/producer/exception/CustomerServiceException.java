package com.prokarma.retail.customer.service.producer.exception;

public class CustomerServiceException extends ApiException {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private int code;

  public CustomerServiceException(int code, String msg) {
    super(code, msg);
    this.code = code;
  }

}

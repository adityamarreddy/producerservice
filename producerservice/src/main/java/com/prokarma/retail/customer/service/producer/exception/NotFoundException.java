package com.prokarma.retail.customer.service.producer.exception;

@javax.annotation.Generated(
    value = "com.prokarma.retail.customer.service.producer.codegen.v3.generators.java.SpringCodegen",
    date = "2020-04-18T05:15:19.983Z[GMT]")
public class NotFoundException extends ApiException {
  private int code;

  public NotFoundException(int code, String msg) {
    super(code, msg);
    this.code = code;
  }
}

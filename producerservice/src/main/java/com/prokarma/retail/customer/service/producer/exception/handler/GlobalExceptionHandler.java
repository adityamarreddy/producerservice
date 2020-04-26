package com.prokarma.retail.customer.service.producer.exception.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import com.prokarma.retail.customer.service.producer.exception.CustomerServiceException;
import com.prokarma.retail.customer.service.producer.model.Response;
import com.prokarma.retail.customer.service.producer.model.Response.StatusEnum;

// ResponseEntityExceptionHandler
@ControllerAdvice
public class GlobalExceptionHandler {
  private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(CustomerServiceException.class)
  public ResponseEntity<Response> handleCustomerServiceException(Exception e) {
    LOGGER.error("CustomerServiceException handler executed");
    return new ResponseEntity<Response>(new Response().message("Customer creation is failed")
        .status(StatusEnum.ERROR).errorType(e.getClass().getName()),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(InterruptedException.class)
  public ResponseEntity<Response> handleInterruptedException(Exception e) {
    LOGGER.error(e.getMessage(), e);
    return new ResponseEntity<Response>(new Response().message("Customer creation is failed")
        .status(StatusEnum.ERROR).errorType(e.getClass().getName()),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ExecutionException.class)
  public ResponseEntity<Response> handleExecutionException(Exception e) {
    LOGGER.error(e.getMessage(), e);
    return new ResponseEntity<Response>(new Response().message("Customer creation is failed")
        .status(StatusEnum.ERROR).errorType(e.getClass().getName()),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class,
      ValidationException.class})
  public ResponseEntity<Response> handleValidationException(Exception e) {
    LOGGER.error(e.getMessage(), e);
    List<String> details = new ArrayList<>();
    if (e instanceof MethodArgumentNotValidException) {

      for (ObjectError error : ((MethodArgumentNotValidException) e).getBindingResult()
          .getAllErrors()) {
        if (error instanceof FieldError) {
          details.add(((FieldError) error).getObjectName() + "." + ((FieldError) error).getField()
              + "->" + ((FieldError) error).getDefaultMessage());
        } else {
          details.add(error.getObjectName() + "->" + error.getDefaultMessage());
        }
      }
    }


    return new ResponseEntity<Response>(
        new Response().message(details.size() > 0 ? details.toString() : e.getMessage())
            .status(StatusEnum.ERROR).errorType(e.getClass().getName()),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<Response> handleNoHandlerFoundException(Exception e) {
    LOGGER.error(e.getMessage(), e);
    return new ResponseEntity<Response>(new Response().message(e.getMessage())
        .status(StatusEnum.ERROR).errorType(e.getClass().getName()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({InvalidTokenException.class, AuthenticationException.class,
      OAuth2Exception.class})
  public ResponseEntity<Response> handleAuthenticationException(Exception e) {
    LOGGER.error(e.getMessage(), e);
    return new ResponseEntity<Response>(new Response().message(e.getMessage())
        .status(StatusEnum.ERROR).errorType(e.getClass().getName()), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Response> handleAllExceptions(Exception e) {
    LOGGER.error(e.getMessage(), e);
    return new ResponseEntity<Response>(new Response().message(e.getMessage())
        .status(StatusEnum.ERROR).errorType(e.getClass().getName()),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  // @Override
  // protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException e,
  // HttpHeaders headers, HttpStatus status, WebRequest request) {
  //
  // LOGGER.error(e.getMessage(), e);
  // return new ResponseEntity<Object>(new Response().message(e.getMessage())
  // .status(StatusEnum.ERROR).errorType(e.getClass().getName()), HttpStatus.BAD_REQUEST);
  // }
}


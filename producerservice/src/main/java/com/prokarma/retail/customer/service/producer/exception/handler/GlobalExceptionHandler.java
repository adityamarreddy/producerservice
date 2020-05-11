package com.prokarma.retail.customer.service.producer.exception.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.validation.ValidationException;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.prokarma.retail.customer.service.producer.exception.ProducerServiceException;
import com.prokarma.retail.customer.service.producer.model.Response;
import com.prokarma.retail.customer.service.producer.model.Response.StatusEnum;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(InterruptedException.class)
  public ResponseEntity<Response> handleInterruptedException(Exception ex) {
    return new ResponseEntity<>(
        new Response().message("Customer creation is failed::" + ex.getMessage())
            .status(StatusEnum.ERROR).errorType(ex.getClass().getName()),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ExecutionException.class)
  public ResponseEntity<Response> handleExecutionException(Exception ex) {
    return new ResponseEntity<>(
        new Response().message("Customer creation is failed::" + ex.getMessage())
            .status(StatusEnum.ERROR).errorType(ex.getClass().getName()),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class,
      ValidationException.class})
  public ResponseEntity<Response> handleValidationException(Exception ex) {
    List<String> details = new ArrayList<>();
    if (ex instanceof MethodArgumentNotValidException) {

      for (ObjectError error : ((MethodArgumentNotValidException) ex).getBindingResult()
          .getAllErrors()) {
        if (error instanceof FieldError) {
          details.add(((FieldError) error).getObjectName() + "." + ((FieldError) error).getField()
              + "->" + ((FieldError) error).getDefaultMessage());
        } else {
          details.add(error.getObjectName() + "->" + error.getDefaultMessage());
        }
      }
    }
    return new ResponseEntity<>(new Response()
        .message(
            "Input validation error::" + (details.isEmpty() ? ex.getMessage() : details.toString()))
        .status(StatusEnum.ERROR).errorType(ex.getClass().getName()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<Response> handleNoHandlerFoundException(Exception ex) {
    return new ResponseEntity<>(new Response().message(ex.getMessage()).status(StatusEnum.ERROR)
        .errorType(ex.getClass().getName()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({InvalidTokenException.class, AuthenticationException.class,
      OAuth2Exception.class})
  public ResponseEntity<Response> handleAuthenticationException(Exception ex) {
    return new ResponseEntity<>(new Response().message(ex.getMessage()).status(StatusEnum.ERROR)
        .errorType(ex.getClass().getName()), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler({ProducerServiceException.class, JsonProcessingException.class,
      Exception.class})
  public ResponseEntity<Response> handleAllExceptions(Exception ex) {
    return new ResponseEntity<>(new Response().message(ex.getMessage()).status(StatusEnum.ERROR)
        .errorType(ex.getClass().getName()), HttpStatus.INTERNAL_SERVER_ERROR);
  }

}


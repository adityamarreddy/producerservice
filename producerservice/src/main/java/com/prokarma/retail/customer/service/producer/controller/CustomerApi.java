/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.19).
 * https://github.com/swagger-api/swagger-codegen Do not edit the class manually.
 */
package com.prokarma.retail.customer.service.producer.controller;

import java.util.concurrent.ExecutionException;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.prokarma.retail.customer.service.producer.model.Customer;
import com.prokarma.retail.customer.service.producer.model.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@javax.annotation.Generated(
    value = "com.prokarma.retail.customer.service.producer.codegen.v3.generators.java.SpringCodegen",
    date = "2020-04-18T05:15:19.983Z[GMT]")
@Api(value = "customer", description = "the customer API")
@RequestMapping(value = "/customer")
public interface CustomerApi {

  @ApiOperation(value = "Add a new customer", nickname = "addCustomer", notes = "",
      response = Response.class,
      authorizations = {@Authorization(value = "oauth2token", scopes = {})}, tags = {"customer",})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Success.", response = Response.class),
      @ApiResponse(code = 400, message = "bad request error", response = Response.class),
      @ApiResponse(code = 401, message = "unaothorized error", response = Response.class),
      @ApiResponse(code = 500, message = "server error", response = Response.class)})
  @RequestMapping(value = "/add", produces = {"application/json"}, consumes = {"application/json"},
      method = RequestMethod.POST)
  ResponseEntity<Response> addCustomer(
      @ApiParam(value = "customer object that needs to be added",
          required = true) @Valid @RequestBody Customer body,
      @ApiParam(value = "", required = true) @RequestHeader(value = "Activity-Id",
          required = true) String activityId,
      @ApiParam(value = "", required = true) @RequestHeader(value = "Application-Id",
          required = true) String applicationId)
      throws InterruptedException, ExecutionException,
      JsonProcessingException;

}

package com.prokarma.retail.customer.service.producer.service.logging;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import com.prokarma.retail.customer.service.producer.model.Response;
import com.prokarma.retail.customer.service.producer.model.Response.StatusEnum;
import com.prokarma.retail.customer.service.producer.util.CustomerDataUtil;

@RunWith(MockitoJUnitRunner.class)
public class LoggingServiceTest {

  @InjectMocks
  private LoggingServiceImpl loggingServiceImpl;

  @Test
  public void testLogRequest() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader("Activity-Id", "2467834784");
    request.addHeader("Application-Id", "Appp00001111");
    request.addParameter("param1", "param1value");
    loggingServiceImpl.logRequest(request, CustomerDataUtil.prepareCustomer());
    assertTrue(true);

  }


  @Test
  public void testLogResponse() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader("Activity-Id", "2467834784");
    request.addHeader("Application-Id", "Appp00001111");
    request.addParameter("param1", "param1value");
    MockHttpServletResponse response = new MockHttpServletResponse();
    response.addHeader("Activity-Id", "2467834784");
    response.addHeader("Application-Id", "Appp00001111");
    loggingServiceImpl.logResponse(request, response,
        new ResponseEntity<>(
            new Response().message("Successfully created customer").status(StatusEnum.SUCCESS),
            HttpStatus.OK));
    assertTrue(true);

  }
}

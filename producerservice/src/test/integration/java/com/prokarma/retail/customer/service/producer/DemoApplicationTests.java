package com.prokarma.retail.customer.service.producer;


import static io.restassured.RestAssured.with;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import io.restassured.RestAssured;
import io.restassured.http.Header;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProducerServiceApplication.class)
@TestPropertySource(value = {"classpath:application-integration-test.properties"})
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class DemoApplicationTests {


  @Value("${server.port}")
  int port;

  @Before
  public void setBaseUri() {
    RestAssured.port = port;
  }

  @Test
  public void testCustomerList() throws InterruptedException {
    with().body(
        "{   \"customerNumber\": \"C000004\",   \"firstName\": \"dakjfsdlfkjsldf\",   \"lastName\": \"reddymarreddy\",   \"birthDate\": \"1989-03-09\",   \"country\": \"India\",   \"countryCode\": \"IN\",   \"mobileNumber\": 231107009,   \"email\": \"ussdf@gmail.com\",   \"customerStatus\": \"Open\",   \"address\": {     \"addressLine1\": \"Line1\",     \"addressLine2\": \"Line2\",     \"street\": \"StreetName\",     \"postalCode\": \"50009\"   } }")
        .header(new Header("Activity-Id", "2467834784"))
        .header(new Header("Application-Id", "clientapplication"))
        .header(new Header("Content-Type", "application/json")).when()
        .request("POST", "/customer/add").then().statusCode(200);
  }

   @Test
  public void contextLoads() {}



}

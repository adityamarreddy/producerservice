package com.prokarma.retail.customer.service.producer;


import static io.restassured.RestAssured.with;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.junit4.SpringRunner;
import io.restassured.http.Header;

@RunWith(SpringRunner.class)
//@EmbeddedKafka
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
    properties = {"server.port=8099","spring.enableoauth2=false","spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration"})
// @EnableAutoConfiguration(exclude=SecurityAutoConfiguration.class)

public class DemoApplicationTests {

//  @Autowired
//  private EmbeddedKafkaBroker embeddedKafkaBroker;

  @Test
  public void testCustomerList() {
    with().body(
        "{   \"customerNumber\": \"C000004\",   \"firstName\": \"dakjfsdlfkjsldf\",   \"lastName\": \"reddymarreddy\",   \"birthDate\": \"1989-03-09\",   \"country\": \"India\",   \"countryCode\": \"IN\",   \"mobileNumber\": 231107009,   \"email\": \"ussdf@gmail.com\",   \"customerStatus\": \"Open\",   \"address\": {     \"addressLine1\": \"Line1\",     \"addressLine2\": \"Line2\",     \"street\": \"StreetName\",     \"postalCode\": \"50009\"   } }")
        .header(new Header("Activity-Id", "2467834784"))
        .header(new Header("Application-Id", "clientapplication"))
        .header(new Header("Content-Type", "application/json")).when()
        .request("POST", "localhost:8099/customer/add").then().statusCode(200);
  }
  


}

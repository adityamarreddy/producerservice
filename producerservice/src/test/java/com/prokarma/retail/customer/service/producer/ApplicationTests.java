package com.prokarma.retail.customer.service.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
    properties = "{spring.enableoauth2=true,spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration}")
public class ApplicationTests {

  @Test
  public void contextLoads() {}

}

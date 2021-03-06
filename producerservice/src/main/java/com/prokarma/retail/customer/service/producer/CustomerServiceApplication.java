package com.prokarma.retail.customer.service.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableAuthorizationServer
@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = {"com.prokarma.retail.customer.service.producer","com.prokarma.retail.customer.service.producer.controller",
    "com.prokarma.retail.customer.service.producer.configuration"})
public class CustomerServiceApplication {


  public static void main(String[] args) throws Exception {
    new SpringApplication(CustomerServiceApplication.class).run(args);
  }


}

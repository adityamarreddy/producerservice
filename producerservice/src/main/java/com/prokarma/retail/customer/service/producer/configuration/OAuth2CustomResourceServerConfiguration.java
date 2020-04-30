package com.prokarma.retail.customer.service.producer.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@ConditionalOnProperty(prefix = "spring", name = "enableoauth2", havingValue = "true")
@EnableAuthorizationServer
@EnableResourceServer
public class OAuth2CustomResourceServerConfiguration extends ResourceServerConfigurerAdapter {
  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().antMatchers("/customer/**").authenticated().antMatchers("/")
        .permitAll();
  }
}

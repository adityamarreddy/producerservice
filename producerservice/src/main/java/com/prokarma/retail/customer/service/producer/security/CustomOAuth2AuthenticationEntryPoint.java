package com.prokarma.retail.customer.service.producer.security;

import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;

public class CustomOAuth2AuthenticationEntryPoint extends OAuth2AuthenticationEntryPoint {

  public CustomOAuth2AuthenticationEntryPoint() {
    super.setExceptionTranslator(new CustomOAuth2WebResponseExceptionTranslator());
  }
}

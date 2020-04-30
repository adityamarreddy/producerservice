package com.prokarma.retail.customer.service.producer.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@javax.annotation.Generated(
    value = "com.prokarma.retail.customer.service.producer.codegen.v3.generators.java.SpringCodegen",
    date = "2020-04-18T05:15:19.983Z[GMT]")
@Configuration
public class SwaggerDocumentationConfiguration extends WebMvcConfigurerAdapter {
  
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {

      registry
              .addResourceHandler("swagger-ui.html")
              .addResourceLocations("classpath:/META-INF/resources/");

      registry
              .addResourceHandler("/webjars/**")
              .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }


  ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("PK Global Customer api")
        .description("This is server app for CustomerServiceApi server.").license("Apache 2.0")
        .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html").termsOfServiceUrl("")
        .version("1.0.0").contact(new Contact("", "", "amarreddy@pkglobal.com")).build();
  }

  @Bean
  public Docket customImplementation() {
    return new Docket(DocumentationType.SWAGGER_2).select()
        .apis(RequestHandlerSelectors.basePackage("com.prokarma.retail.customer.service.producer.controller")).build()
        .directModelSubstitute(org.threeten.bp.LocalDate.class, java.sql.Date.class)
        .directModelSubstitute(org.threeten.bp.OffsetDateTime.class, java.util.Date.class)
        .apiInfo(apiInfo());
  }

}

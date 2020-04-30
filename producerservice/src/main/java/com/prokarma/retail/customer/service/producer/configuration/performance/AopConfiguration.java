package com.prokarma.retail.customer.service.producer.configuration.performance;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@Aspect
public class AopConfiguration {

  @Pointcut("execution(* com.prokarma.retail.customer.service.producer.service.CustomerService.*(..)) || execution(* com.prokarma.retail.customer.service.producer.controller.ProducerServiceController.*(..))")
  public void monitor() {
    // pointcut expression for performance monitoring
  }

  @Bean
  public PerformanceMonitorInterceptor performanceMonitorInterceptor() {
    return new PerformanceMonitorInterceptor(false);
  }

  @Bean
  public Advisor performanceMonitorAdvisor() {
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    pointcut.setExpression(
        "com.prokarma.retail.customer.service.producer.configuration.performance.AopConfiguration.monitor()");
    return new DefaultPointcutAdvisor(pointcut, performanceMonitorInterceptor());
  }


}

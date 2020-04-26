//package com.prokarma.retail.customer.service.producer.configuratioin.interceptor;
//
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//import com.prokarma.retail.customer.service.producer.exception.handler.GlobalExceptionHandler;
//
//@Component
//public class RequestResponseInterceptor implements HandlerInterceptor {
//  private static final Logger LOGGER = LoggerFactory.getLogger(RequestResponseInterceptor.class);
//
//  @Override
//  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//      throws Exception {
//
//    //LOGGER.info(request.getMethod()+"::"+request.get);
//
//    return true;
//  }
//
//  @Override
//  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//      ModelAndView modelAndView) throws Exception {
//
//    System.out.println("Post Handle method is Calling");
//  }
//
//  @Override
//  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
//      Object handler, Exception exception) throws Exception {
//
//    System.out.println("Request and Response is completed");
//  }
//}

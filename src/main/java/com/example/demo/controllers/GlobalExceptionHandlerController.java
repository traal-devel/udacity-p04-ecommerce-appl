package com.example.demo.controllers;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * Global Expcetion Handler Controller.
 * 
 * @author traal-devel
 */
@RestControllerAdvice
public class GlobalExceptionHandlerController {

  
  /* constants */
  private static final Logger logger = 
                LoggerFactory.getLogger(GlobalExceptionHandlerController.class);
  
  
  /* member variables */
  
  
  /* constructors */
  public GlobalExceptionHandlerController() {
    super();
  }
  
  
  /* methods */
  @Bean
  public ErrorAttributes errorAttributes() {
    // Do not show exception field in the http resonse.
    return new DefaultErrorAttributes() {
      
      /*  methods */
      @Override
      public Map<String, Object> getErrorAttributes(
          WebRequest webRequest, 
          boolean includeStackTrace
      ) {
        Map<String, Object> errorAttributes = 
                    super.getErrorAttributes(webRequest, includeStackTrace);
        errorAttributes.remove("exception");
        return errorAttributes;
      }
    };
  }

  @ExceptionHandler(Exception.class)
  public void handleException(
      HttpServletResponse res,
      Exception ex
  ) throws IOException {
    
    logger.error("Handle exception", ex);
    res.sendError(HttpStatus.BAD_REQUEST.value(), "Something went wrong");
  }

}

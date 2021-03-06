package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * eCommerce Spring Boot Application starting point. 
 * 
 * @author traal-devel
 */
@EnableJpaRepositories(
    value = "com.example.demo.model.persistence.repositories"
)
@EntityScan(
    value = "com.example.demo.model.persistence"
)
// :INFO: Spring Boot ships with an automatically configured security module 
// that must be disabled, as we will be implementing our own. This must be 
// done in the Application class.
@SpringBootApplication(
    exclude = {SecurityAutoConfiguration.class}
)
public class SareetaApplication {

  
  /* member variables */
  
  
  /* constructors */
  public SareetaApplication() {
    super();
  }
  
  
  /* methods */
	public static void main(
	    String[] args
	) {
		SpringApplication.run(SareetaApplication.class, args);
	}
	

}

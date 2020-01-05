package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * eCommerce Spring Boot Application starting point. 
 * 
 * @author traal-devel
 */
@EnableJpaRepositories("com.example.demo.model.persistence.repositories")
@EntityScan("com.example.demo.model.persistence")
@SpringBootApplication
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

package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.def.SecurityConstants;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  
  /* member variables */
  @Autowired
  private UserDetailsServiceImpl userDetailsService;
  
  @Autowired
  private BCryptPasswordEncoder  passwordEncoder;
  
  /* constructors */
  public WebSecurityConfiguration() {
    super();
  }
  
  
  /* methods */
  @Override
  protected void configure(
      HttpSecurity http
  ) throws Exception {
  
    http.cors().and().csrf().disable().authorizeRequests()
        .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
        .anyRequest().authenticated()
        .and()
        .addFilter(new JWTAuthenticationFilter(this.authenticationManager()))
        .addFilter(new JWTAutenticationVerificationFilter(this.authenticationManager()))
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    ;
  }
  
  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
  
  @Override
  protected void configure(
      AuthenticationManagerBuilder auth
  ) throws Exception {
    
    auth.parentAuthenticationManager(this.authenticationManagerBean())
        .userDetailsService(this.userDetailsService)
        .passwordEncoder(this.passwordEncoder)
        ;
    
  }
  
  

}

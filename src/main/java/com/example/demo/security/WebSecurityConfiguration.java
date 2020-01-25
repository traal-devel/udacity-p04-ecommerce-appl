package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.def.SecurityConstants;

/**
 * Web security configuration.
 * <p>
 * Attaches the user details service implementation to Spring's 
 * AuthenticationManager. It also handles session management and what 
 * endpoints are secured. 
 * </p>
 * <p>
 * The session is managed by ourself so Spring's session management should 
 * be disabled (in Spring's Application class). Filters were added to the 
 * authentication chain and every endpoint but 1 should have security required. 
 * The one that should not is the one responsible for creating new users.
 * </p>
 * 
 * @author traal-devel
 */
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  
  /* member variables */
  
  
  /* constructors */
  public WebSecurityConfiguration() {
    super();
  }
  
  
  /* methods */
  @Override
  protected void configure(
      HttpSecurity http
  ) throws Exception {
    http.headers().frameOptions().disable();            // for h2
    http.cors().and().csrf().disable().authorizeRequests()
        .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
        .antMatchers("/h2", "/h2/**").permitAll()       // for tests
        .anyRequest().authenticated()
        .and()
        .addFilter(new JWTAuthenticationFilter(this.authenticationManager()))
        .addFilter(new JWTAutenticationVerificationFilter(this.authenticationManager()))
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    ;
    http.apply(
          new JwtTokenFilterConfigurer(
              new JWTAuthenticationFilter(this.authenticationManager()),
              new JWTAutenticationVerificationFilter(this.authenticationManager())
          ));
  }
  
  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
  
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }
  
  // :INFO:jk, 25.01.2020: This is an error. Do not try to define the beans
  // UserDetailSevice and PasswordEncoder this way or you will loop forever
  // in the class: 
  //
  // - org.springframework.security.authentication.ProviderManager
  //
  // because of the fact that you set the parent and therefore the line: 
  //
  // -> result = parentResult = parent.authenticate(authentication);
  // 
  // will be invoked over and over again.
//  @Override
//  protected void configure(
//      AuthenticationManagerBuilder auth
//  ) throws Exception {
//    
//    auth.parentAuthenticationManager(this.authenticationManagerBean())
//        .userDetailsService(this.userDetailsService)
//        .passwordEncoder(this.passwordEncoder)
//        ;
//    
//  }
  
  

}

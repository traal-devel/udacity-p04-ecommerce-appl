package com.example.demo.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.def.SecurityConstants;

/**
 * This filter is used to set the JWT-Token into the spring security context.
 * <p>
 * If a JWT-Token is sent it will be checked. If it is valid then the 
 * correspond Authentication object will be set into the spring security 
 * context for later use (eg. rest controller). 
 * </p>
 * 
 * @author traal-devel
 */
@Component
public class JWTAutenticationVerificationFilter   
                                extends BasicAuthenticationFilter {

  
  /* member variables */

  
  /* constructors */
  /**
   * Constructor for JWTAutenticationVerificationFilter.
   * 
   * @param authenticationManager {@link AuthenticationManager}
   */
  public JWTAutenticationVerificationFilter(
      AuthenticationManager authenticationManager
  ) {
  
    super(authenticationManager);

  }

  /* methods */
  @Override
  protected void doFilterInternal(
      HttpServletRequest request, 
      HttpServletResponse response, 
      FilterChain chain
  ) throws IOException, ServletException {

    String header = request.getHeader(SecurityConstants.HEADER_AUTHORIZATION);
    
    if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
      chain.doFilter(request, response);
    } else {
      UsernamePasswordAuthenticationToken authToken = 
                                    this.getAuthentication(request);
      SecurityContextHolder.getContext().setAuthentication(authToken);
      chain.doFilter(request, response);
    }
  
  }
  
  private UsernamePasswordAuthenticationToken getAuthentication(
      HttpServletRequest request
  ) {
    UsernamePasswordAuthenticationToken authToken = null;
    String token = request.getHeader(SecurityConstants.HEADER_AUTHORIZATION);
    if (token != null) {
      String user = 
          JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()))
             .build()
             .verify(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
             .getSubject();
      if (user != null) {
        authToken = new UsernamePasswordAuthenticationToken (
            user, 
            null, 
            new ArrayList<>()
        );
      }
    }
    
    return authToken;
    
  }
  

}

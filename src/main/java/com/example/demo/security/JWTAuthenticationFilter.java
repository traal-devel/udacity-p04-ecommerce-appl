package com.example.demo.security;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.example.demo.def.SecurityConstants;
import com.example.demo.model.requests.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Checks login request by a client.
 * <p>
 * Takes the username and password from a login request and logging in. 
 * This, upon successful authentication, should hand back a valid JWT 
 * in the Authorization header
 * </p>
 * 
 * @author traal-devel
 */
public class JWTAuthenticationFilter 
                        extends UsernamePasswordAuthenticationFilter {

  
  /* member variables */
  private AuthenticationManager authManager;
  
  
  /* constructors */
  public JWTAuthenticationFilter(
      AuthenticationManager authManager
  ) {
    
    super();
    this.authManager = authManager;
    
  }
  
  
  /* methods */
  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, 
      HttpServletResponse response
  ) throws AuthenticationException {
    Authentication auth = null;
    try {
      
      LoginRequest credentials = 
          new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
      auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                      credentials.getUsername(),
                      credentials.getPassword(),
                      new ArrayList<>())
      );
    } catch (IOException ioEx) {
      throw new RuntimeException(ioEx);
    }
    
    return auth;
  }
  
  @Override
  protected void successfulAuthentication(
      HttpServletRequest request, 
      HttpServletResponse response, 
      FilterChain chain,
      Authentication authResult
  ) throws IOException, ServletException {
  
    User user = (User)authResult.getPrincipal();
    Date expiresDate = new Date(System.currentTimeMillis() 
                                  + SecurityConstants.EXPIRATION_TIME);
    String token = 
        JWT.create()
           .withSubject(user.getUsername())
           .withExpiresAt(expiresDate)
           .sign(HMAC512(SecurityConstants.SECRET.getBytes()));
    
    response.addHeader(
        SecurityConstants.HEADER_AUTHORIZATION, 
        SecurityConstants.TOKEN_PREFIX + token);
  }

}

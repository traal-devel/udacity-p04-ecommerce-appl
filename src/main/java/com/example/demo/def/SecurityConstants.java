package com.example.demo.def;

/**
 * Different security constants used for JWT integration.
 * 
 * @author traal-devel
 */
public class SecurityConstants {

  
  /* constatns */
  public static final long   EXPIRATION_TIME  = 864_000_000; // 10 days
  public static final String HEADER_STRING    = "Authorization";
  public static final String TOKEN_PREFIX     = "Bearer ";
  public static final String SECRET           = "oursecretkey";
  public static final String SIGN_UP_URL      = "/api/user/create";
  
  
  /* member variables */

  
  /* constructors */
  private SecurityConstants() {
    super();
  }
  
  /* methods */

}

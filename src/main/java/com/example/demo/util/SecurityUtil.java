package com.example.demo.util;

import java.security.SecureRandom;

public class SecurityUtil {

  
  /* member variables */
  private static SecureRandom random = new SecureRandom();
  
  /* constructors */
  private SecurityUtil() {
    super();
  }
  
  /* methods */
  public static String generateRanomSalt() {
    byte[] salt = new byte[16];
    random.nextBytes(salt);
    return new String(salt);
  }

}

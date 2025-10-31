package com.oliver.portfolio.service.impl;

import com.oliver.portfolio.model.User;
import com.oliver.portfolio.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
  
  private final String SECRET_KEY = "14C29B6C24C38DA54797A582A7F55j1Y4N8QlUSATU94a44Q9weNGbx4PuiPy";
  
  private static final Logger LOGGER =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  
  @Override
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }
  
  @Override
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }
  
  @Override
  public String generateToken(User user) {
    LOGGER.info("Generating token");
    return generateToken(new HashMap<>(), user);
  }
  
  @Override
  public String generateToken(Map<String, Object> claims, User user) {
    return Jwts.builder()
              .setClaims(claims)
              .setSubject(user.getUsername())
              .setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
              .signWith(getSignInKey(), SignatureAlgorithm.HS256)
              .compact();
  }
  
  @Override
  public boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return(username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
  
  private boolean isTokenExpired(String token) {
    return(extractExpirationDate(token).before(new Date()));
  }
  
  private Date extractExpirationDate(String token) {
    return(extractClaim(token, Claims::getExpiration));
  }
  
  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }
  
  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}

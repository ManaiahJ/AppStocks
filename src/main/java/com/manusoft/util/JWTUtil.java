package com.manusoft.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Base64;
import java.util.Date;


public class JWTUtil {

    private static final String SECRET_KEY = "MAStockAlerts";

   public static String AUTHENTICATION_COOKIE_NAME = "X-Client-Key";
   public static String USER_ID_COOKIE_NAME = "X-Auth-User-Key";

    private static final long EXPIRATION_TIME = 5 * 60 * 1000;

   public static String generateToken(String username) {
       long nowMillis = System.currentTimeMillis();
       Date now = new Date(nowMillis);
       Date expiryDate = new Date(nowMillis + EXPIRATION_TIME);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(SECRET_KEY.getBytes()))
                .compact();
    }

    public static Claims getClaims(String token) {
        return Jwts.parser().
                setSigningKey(Base64.getEncoder().encodeToString(SECRET_KEY.getBytes()))
                .parseClaimsJws(token)
                .getBody();
    }

    public static boolean isTokenValid(String token) {
        return getClaims(token).getExpiration().getMinutes() > 5;
    }

    public static  String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public static  boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    public static Date extractExpiration(String token) {
        return getClaims(token).getExpiration();
    }
}

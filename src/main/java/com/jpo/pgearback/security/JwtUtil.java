package com.jpo.pgearback.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${security.jwt.secret-key}")
    private String v_secretKey;

    public String generateToken(String p_username) {
        Map<String, Object> v_claims = new HashMap<>();
        return createToken(v_claims, p_username);
    }

    private String createToken(Map<String, Object> p_claims, String p_username) {
        return Jwts.builder()
                .setClaims(p_claims)
                .setSubject(p_username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                //30min
                .setExpiration(new Date(System.currentTimeMillis() + 10000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    //return a key
    private Key getSignKey() {
        byte[] v_keybytes = Decoders.BASE64.decode(v_secretKey);
        return Keys.hmacShaKeyFor(v_keybytes);
    }

    //extract username from token
    public String extractUsername(String p_token) {
        return extractClaim(p_token, Claims::getSubject);
    }

    public <T> T extractClaim (String p_token, Function<Claims, T> p_claimsResolver) {
        final Claims v_claims = extractAllClaims(p_token);
        return p_claimsResolver.apply(v_claims);
    }

    public Claims extractAllClaims(String p_token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(p_token).getBody();
    }

    private Boolean isTokenExpired(String p_token) {
        return extractExpiration(p_token).before(new Date());
    }

    public Date extractExpiration(String p_token) {
        return extractClaim(p_token, Claims::getExpiration);
    }

    public Boolean validateToken(String p_token, UserDetails p_userDetails) {
        final String v_username = extractUsername(p_token);
        return (v_username.equals(p_userDetails.getUsername()) && !isTokenExpired(p_token));
    }

}

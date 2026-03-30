package org.security.jwt;

import org.security.contract.AuthUser;
import org.security.properties.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.List;

public class JwtTokenProvider {

    private final SecretKey key;
    private final SecurityProperties properties;

    public JwtTokenProvider(SecurityProperties properties) {
        this.properties = properties;
        this.key = Keys.hmacShaKeyFor(properties.getSecret().getBytes());
    }

    public String generateToken(AuthUser user) {

        Date now = new Date();
        Date expiry = new Date(now.getTime() + properties.getExpiration());

        return Jwts.builder()
                .subject(user.getUsername())
                .claim("userId", user.getUserId())
                .claim("roles", user.getRoles())
                .issuedAt(now)
                .expiration(expiry)
                .signWith(key)
                .compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUsername(Claims claims) {
        return claims.getSubject();
    }

    public Long getUserId(Claims claims) {
        return claims.get("userId", Long.class);
    }

    public List<String> getRoles(Claims claims) {
        return claims.get("roles", List.class);
    }
}
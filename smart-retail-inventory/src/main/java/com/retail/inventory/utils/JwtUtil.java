package com.retail.inventory.utils;

import com.retail.inventory.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtProperties jwtProperties;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(
                jwtProperties.getSecretKey()
                        .getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateToken(Long userId,
                                String username) {

        Date now = new Date();

        Date expireDate = new Date(
                now.getTime() + jwtProperties.getExpiration()
        );

        return Jwts.builder()
                .subject(username)
                .claim("userId", userId)
                .issuedAt(now)
                .expiration(expireDate)
                .signWith(getKey())
                .compact();
    }

    public Claims parseToken(String token) {

        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Long getUserId(String token) {

        Claims claims = parseToken(token);

        return claims.get("userId", Long.class);
    }

    public String getUsername(String token) {

        Claims claims = parseToken(token);

        return claims.getSubject();
    }
}

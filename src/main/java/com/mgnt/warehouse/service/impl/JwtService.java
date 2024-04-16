package com.mgnt.warehouse.service.impl;

import com.mgnt.warehouse.modal.auth.UserPrinciple;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@Slf4j
public class JwtService {
    @Value("${security.jwt.secret-key}")
    private String jwtSecret;

    @Value("${security.jwt.secret-key}")
    private long jwtExpiration;

    public String generateJwtToken(Authentication authentication) {

        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();

        return Jwts.builder()
                .subject((userPrincipal.getUsername()))
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpiration * 1000))
                .signWith(getSignInKey(), Jwts.SIG.HS512)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token -> Message: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token -> Message: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token -> Message: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty -> Message: {}", e.getMessage());
        }

        return false;
    }
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}

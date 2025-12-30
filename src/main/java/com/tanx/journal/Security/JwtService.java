package com.tanx.journal.Security;

import com.tanx.journal.Entity.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Data
@Component
public class JwtService {

    @Autowired
    private JwtProps jwtProps;
    private Key key;

    private Key getSigningKey(){
        if(key == null){
            key = Keys.hmacShaKeyFor(jwtProps.getSecret().getBytes());
        }
        return key;
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver){
        Claims claims = Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return resolver.apply(claims);
    }

    public String generateToken(CustomUserDetails userDetails){
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtProps.getExpirationMs());
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        for(String s : roles) System.out.println(roles);

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .signWith(getSigningKey())
                .issuedAt(new Date())
                .claim("roles", roles)
                .expiration(expiry)
                .compact();
    }

    public boolean isTokenValid(String token, CustomUserDetails userDetails){
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        Date expiry = extractClaim(token, Claims::getExpiration);
        return expiry.before(new Date());
    }
}

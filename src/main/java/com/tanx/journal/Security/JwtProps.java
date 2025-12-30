package com.tanx.journal.Security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jwt")
@AllArgsConstructor
@RequiredArgsConstructor
public class JwtProps {
    private String secret;
    private long expirationMs;
}

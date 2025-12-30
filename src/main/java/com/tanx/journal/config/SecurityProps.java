package com.tanx.journal.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "app.security")
public class SecurityProps {
    private List<String> publicPaths;
    private List<String> securedPaths;
    private List<String> adminPaths;
    private List<String> kafkaPaths;

}

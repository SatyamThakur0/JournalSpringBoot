package com.tanx.journal.config;

import com.tanx.journal.Security.JwtAuthenticationFilter;
import com.tanx.journal.Security.JwtProps;
import com.tanx.journal.Services.UserEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties({SecurityProps.class, JwtProps.class})
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    private final SecurityProps securityProps;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserEntryService userEntryService;


    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userEntryService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
        String[] publicPaths = securityProps.getPublicPaths().toArray(new String[0]);
        String[] securedPaths = securityProps.getSecuredPaths().toArray(new String[0]);
        String[] adminPaths = securityProps.getAdminPaths().toArray(new String[0]);
        String[] kafkaPaths = securityProps.getKafkaPaths().toArray(new String[0]);

        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth->auth
                        .requestMatchers(publicPaths).permitAll()
                        .requestMatchers(securedPaths).authenticated()
                        .requestMatchers(kafkaPaths).authenticated()
                        .requestMatchers(adminPaths).hasRole("ADMIN")
                        .anyRequest().denyAll()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}

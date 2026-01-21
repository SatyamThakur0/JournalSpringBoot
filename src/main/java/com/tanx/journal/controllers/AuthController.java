package com.tanx.journal.controllers;

import com.tanx.journal.DTO.LoginRequest;
import com.tanx.journal.DTO.LoginResponse;
import com.tanx.journal.Entity.CustomUserDetails;
import com.tanx.journal.Entity.UserEntry;
import com.tanx.journal.Security.JwtService;
import com.tanx.journal.Services.QuotesApiService;
import com.tanx.journal.Services.UserEntryService;
import com.tanx.journal.pojo.QuoteRes;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("auth")
@Tag(name = "Auth APIs", description = "login, signup") //For Swagger`
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserEntryService userEntryService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private QuotesApiService quotesApiService;

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request, HttpServletResponse response){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        }
        catch (AuthenticationException e){
            log.info("yhi hua h");
            throw new RuntimeException("Invalid credentials");
        }
        CustomUserDetails userDetails = userEntryService.loadUserByUsername(request.getUsername());
        String token = jwtService.generateToken(userDetails);

        ResponseCookie cookie = ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .maxAge((long)3600 * 24)
                .sameSite("None")
                .secure(true)
                .path("/")
                .build();
        response.setHeader("Set-Cookie", cookie.toString());
        List<QuoteRes> res = quotesApiService.getQuote();
        QuoteRes quote = res.getFirst();
        String greet = "Hii " + userDetails.getUsername()+ ", " + quote.getQuote();
        return new ResponseEntity<>(greet, HttpStatus.OK);
    }

    @PostMapping("signup")
    public ResponseEntity<HttpStatus> signUp(@RequestBody LoginRequest request) {
        try {
            UserEntry existing = userEntryService.getUserByUserNameService(request.getUsername());
            UserEntry user = new UserEntry();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.getRoles().add("USER");
            userEntryService.createUserService(user);
        }
        catch (Exception e){
            log.info("Exception INFO : {}", e.getLocalizedMessage());
            log.error("Exception ERROR : {}", e.getLocalizedMessage());
            log.debug("Exception DEBUG : {}", e.getLocalizedMessage());
            log.trace("Exception TRACE : {}", e.getLocalizedMessage());
            log.warn("Exception WARN : {}", e.getLocalizedMessage());
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

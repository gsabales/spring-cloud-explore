package com.appsdeveloperblog.photoappapiusers.ui.security;

import com.appsdeveloperblog.photoappapiusers.ui.model.LoginRequest;
import com.appsdeveloperblog.photoappapiusers.ui.model.UserResponse;
import com.appsdeveloperblog.photoappapiusers.ui.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UserService userService;
    private String tokenSecret;
    private String tokenExpiration;

    public AuthenticationFilter(UserService userService, String tokenSecret, String tokenExpiration,
                                AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.tokenSecret = tokenSecret;
        this.tokenExpiration = tokenExpiration;
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequest credentials = new ObjectMapper()
                    .readValue(request.getInputStream(), LoginRequest.class);

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    credentials.getEmail(),
                    credentials.getPassword(),
                    new ArrayList<>());

            return getAuthenticationManager().authenticate(token);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String username = ((User)authResult.getPrincipal()).getUsername();
        UserResponse userResponse = userService.getUserResponseByEmail(username);
        String token = generateJWT(userResponse.getUserId());

        System.out.println("Token secret from users ms: " + tokenSecret);
        response.addHeader("userId", userResponse.getUserId());
        response.addHeader("token", token);
    }

    private String generateJWT(String userId) throws UnsupportedEncodingException {
        String signingKeyB64= Base64.getEncoder().encodeToString(tokenSecret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(userId)
                .setId(tokenSecret) // TODO: To be removed
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(tokenExpiration)))
                .signWith(SignatureAlgorithm.HS256, signingKeyB64)
                .compact();
    }
}

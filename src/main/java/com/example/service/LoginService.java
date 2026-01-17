package com.example.service;

import com.example.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.inject.Inject;

import org.mindrot.jbcrypt.BCrypt;

import javax.crypto.SecretKey;
import java.util.Date;

import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;

public class LoginService {

    @Inject
    private UserService userService;

    private static final String SECRET = "key-code-moneey-bankk-2025-very-secure-key!!";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String login(String email, String password, String correlationId) {
        User user = userService.getUserByEmail(email);

        if (user == null) {
            userService.saveConnectionHistory(null, "DENIED", correlationId);
            throw new NotFoundException("Email not found");
        }

        if (!BCrypt.checkpw(password, user.getPassword())) {
            userService.saveConnectionHistory(user, "DENIED", correlationId);
            throw new WebApplicationException("Bad credentials");
        }

        userService.saveConnectionHistory(user, "APPROVED", correlationId);

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date exp = new Date(nowMillis + 1800000);

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("id", user.getId())
                .claim("lastname", user.getLastname())
                .claim("firstname", user.getFirstname())
                .claim("birthdate", user.getBirthdate() != null ? user.getBirthdate().toString() : null)
                .issuedAt(now)
                .expiration(exp)
                .signWith(SECRET_KEY)
                .compact();
    }
}

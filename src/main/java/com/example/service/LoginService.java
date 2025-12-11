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

public class LoginService {
    @Inject
    private UserService userService;

    private static final String SECRET = "key-code-moneey-bankk-2025-very-secure-key!!";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String login(String username, String password) {
        User user = userService.getUserByEmail(username);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new NotAuthorizedException("Bad credentials");
        }

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date exp = new Date(nowMillis + 1800000);

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("id", user.getId())
                .claim("lastname", user.getLastName())
                .claim("firstname", user.getFirstName())
                .claim("birthdate", user.getBirthdate())
                .issuedAt(now)
                .expiration(exp)
                .signWith(SECRET_KEY)
                .compact();
    }
}

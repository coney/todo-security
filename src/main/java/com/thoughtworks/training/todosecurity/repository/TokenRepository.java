package com.thoughtworks.training.todosecurity.repository;

import com.thoughtworks.training.todosecurity.exception.UnauthenticatedException;
import com.thoughtworks.training.todosecurity.model.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Repository
public class TokenRepository {

    private static final byte[] SECRET_KEY = "my-secret".getBytes();

    private static final String USER_ID_KEY = "userId";

    public TokenRepository() {
    }

    public String create(User userInDb) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .claim("userId", userInDb.getId())
                .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                .compact();
    }


    public Optional<Integer> get(String token) {
        try {
            return Optional.ofNullable(Jwts.parser().setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody()
                    .get(USER_ID_KEY, Integer.class));
        } catch (JwtException e) {
            // add exception details
            throw new UnauthenticatedException(e.getMessage());
        }
    }
}

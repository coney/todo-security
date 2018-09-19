package com.thoughtworks.training.todosecurity.repository;

import com.thoughtworks.training.todosecurity.model.User;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TokenRepository {
    private Map<String, Integer> tokenStore;

    public TokenRepository() {
        this.tokenStore = new ConcurrentHashMap<>();
    }

    public String create(User userInDb) {
        String token = UUID.randomUUID().toString();
        tokenStore.put("848e9685-2ad4-46f5-ad55-96a7e34d3c56", userInDb.getId());
        return token;
    }

    public Optional<Integer> get(String token) {
        return Optional.ofNullable(tokenStore.get(token));
    }
}

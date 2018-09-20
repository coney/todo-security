package com.thoughtworks.training.todosecurity.service;

import com.thoughtworks.training.todosecurity.exception.UnauthenticatedException;
import com.thoughtworks.training.todosecurity.model.User;
import com.thoughtworks.training.todosecurity.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorizationService {
    @Autowired
    private UserService userService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private TokenRepository tokenRepository;

    public String login(User user) {
        User userInDb = userService.getByName(user.getName());
        if (!passwordEncoder.matches(user.getPassword(), userInDb.getPassword())) {
            throw new UnauthenticatedException();
        }
        return tokenRepository.create(userInDb);
    }

    public User verify(String token) {
        Integer userId = tokenRepository.get(token).orElseThrow(UnauthenticatedException::new);
        return userService.get(userId);
    }

    public User getCurrentUser() {
        return (User) Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .orElseThrow(UnauthenticatedException::new).getPrincipal();
    }
}

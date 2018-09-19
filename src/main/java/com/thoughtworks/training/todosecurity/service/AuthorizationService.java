package com.thoughtworks.training.todosecurity.service;

import com.thoughtworks.training.todosecurity.exception.UnauthenticatedException;
import com.thoughtworks.training.todosecurity.model.User;
import com.thoughtworks.training.todosecurity.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenRepository tokenRepository;

    public String login(User user) {
        User userInDb = userService.getByName(user.getName());
        if (!userInDb.getPassword().equals(user.getPassword())) {
            throw new UnauthenticatedException();
        }
        return tokenRepository.create(userInDb);
    }

    public User verify(String token) {
        Integer userId = tokenRepository.get(token).orElseThrow(UnauthenticatedException::new);
        return userService.get(userId);
    }

}
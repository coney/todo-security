package com.thoughtworks.training.todosecurity.service;

import com.thoughtworks.training.todosecurity.exception.NotFoundException;
import com.thoughtworks.training.todosecurity.model.User;
import com.thoughtworks.training.todosecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> list() {
        return userRepository.findAll();
    }

    public User create(User user) {
        return userRepository.save(user);
    }


    public User getByName(String name) {
        return userRepository.findByName(name).orElseThrow(NotFoundException::new);
    }

    public  User get(Integer userId) {
        return Optional.ofNullable(userRepository.findOne(userId)).orElseThrow(NotFoundException::new);
    }
}

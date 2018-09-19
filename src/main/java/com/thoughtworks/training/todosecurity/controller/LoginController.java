package com.thoughtworks.training.todosecurity.controller;

import com.thoughtworks.training.todosecurity.model.User;
import com.thoughtworks.training.todosecurity.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/login")
public class LoginController {


    @Autowired
    private AuthorizationService authorizationService;

    @PostMapping
    public String create(@RequestBody User user) {
        return authorizationService.login(user);
    }
}

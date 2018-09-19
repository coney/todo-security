package com.thoughtworks.training.todosecurity.security;

import com.google.common.collect.ImmutableList;
import com.thoughtworks.training.todosecurity.exception.UnauthenticatedException;
import com.thoughtworks.training.todosecurity.model.User;
import com.thoughtworks.training.todosecurity.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFilter extends OncePerRequestFilter {
    @Autowired
    private AuthorizationService authorizationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (!StringUtils.isEmpty(token)) {
                User user = authorizationService.verify(token);
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                user,null, ImmutableList.of()
                        )
                );
            }
            filterChain.doFilter(request, response);
        } catch (UnauthenticatedException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}

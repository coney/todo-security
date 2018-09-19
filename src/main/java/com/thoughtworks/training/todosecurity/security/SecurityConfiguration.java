package com.thoughtworks.training.todosecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
//@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private LoginFilter loginFilter;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth)
//      throws Exception {
//        auth
//          .inMemoryAuthentication()
//          .withUser("user")
//            .password("password")
//            .roles("USER")
//            .and()
//          .withUser("admin")
//            .password("admin")
//            .roles("USER", "ADMIN");
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users", "/login").permitAll()
                .anyRequest()
                .authenticated()
                .and().addFilterBefore(loginFilter, BasicAuthenticationFilter.class);
    }
}
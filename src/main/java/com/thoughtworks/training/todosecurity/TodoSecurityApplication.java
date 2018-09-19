package com.thoughtworks.training.todosecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@EntityScan(
        basePackageClasses = {TodoSecurityApplication.class, Jsr310JpaConverters.class}
)
@SpringBootApplication
public class TodoSecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(TodoSecurityApplication.class, args);
    }
}

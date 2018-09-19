package com.thoughtworks.training.todosecurity.repository;

import com.thoughtworks.training.todosecurity.model.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {
    Page<Todo> findAllByTags_NameInAndDueDateIsBetween(String tag, LocalDate from, LocalDate to, Pageable pageable);

    Page<Todo> findAllByDueDateIsBetween(LocalDate from, LocalDate to, Pageable pageable);

    Page<Todo> findAllByTags_NameIn(String tag, Pageable pageable);
}

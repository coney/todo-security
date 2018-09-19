package com.thoughtworks.training.todosecurity.repository;

import com.thoughtworks.training.todosecurity.model.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {
    Page<Todo> findAllByUser_IdAndTags_NameInAndDueDateIsBetween(Integer userId, String tag, LocalDate from, LocalDate to, Pageable pageable);

    Page<Todo> findAllByUser_IdAndDueDateIsBetween(Integer userId, LocalDate from, LocalDate to, Pageable pageable);

    Page<Todo> findAllByUser_IdAndTags_NameIn(Integer userId, String tag, Pageable pageable);

    Page<Todo> findAllByUser_Id(Integer userId, Pageable pageable);
}

package com.thoughtworks.training.todosecurity.service;

import com.google.common.primitives.Booleans;
import com.thoughtworks.training.todosecurity.exception.BadRequestException;
import com.thoughtworks.training.todosecurity.exception.ForbiddenException;
import com.thoughtworks.training.todosecurity.exception.NotFoundException;
import com.thoughtworks.training.todosecurity.model.Todo;
import com.thoughtworks.training.todosecurity.model.User;
import com.thoughtworks.training.todosecurity.repository.TodoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private AuthorizationService authorizationService;

    public Todo get(Integer id) {
        User user = authorizationService.getCurrentUser();
        Todo todo = Optional.ofNullable(todoRepository.findOne(id))
                .orElseThrow(NotFoundException::new);
        if (!todo.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException();
        }
        return todo;
    }

    public Todo create(Todo todo) {
        User user = authorizationService.getCurrentUser();
        todo.setUser(user);
        return todoRepository.save(bindTags(todo));
    }

    private Todo bindTags(Todo todo) {
        todo.setTags(tagService.bind(todo.getTags()));
        return todo;
    }

    public Todo update(Integer id, Todo todo) {
        Todo current = get(id);
        BeanUtils.copyProperties(todo, current, "id");
        return todoRepository.save(bindTags(todo));
    }

    public void remove(Integer id) {
        todoRepository.delete(get(id));
    }

    public Page<Todo> list(Optional<String> tag, Optional<LocalDate> from, Optional<LocalDate> to, Pageable pageable) {
        User user = authorizationService.getCurrentUser();
        if (Booleans.countTrue(from.isPresent(), to.isPresent()) == 1) {
            throw new BadRequestException("from & to must appear in same time");
        }
        // Specification is a better choice
        if (from.isPresent() && tag.isPresent()) {
            return todoRepository.findAllByUser_IdAndTags_NameInAndDueDateIsBetween(user.getId(), tag.get(), from.get(), to.get(), pageable);
        } else if (from.isPresent()) {
            return todoRepository.findAllByUser_IdAndDueDateIsBetween(user.getId(), from.get(), to.get(), pageable);
        } else if (tag.isPresent()) {
            return todoRepository.findAllByUser_IdAndTags_NameIn(user.getId(), tag.get(), pageable);
        } else {
            return todoRepository.findAllByUser_Id(user.getId(), pageable);
        }
    }
}

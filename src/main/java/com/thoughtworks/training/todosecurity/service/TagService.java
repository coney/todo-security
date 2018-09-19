package com.thoughtworks.training.todosecurity.service;

import com.thoughtworks.training.todosecurity.model.Tag;
import com.thoughtworks.training.todosecurity.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public List<Tag> bind(List<Tag> tags) {
        return tags.stream()
                .map(tag -> tagRepository.findByName(tag.getName()).orElse(tag))
                .collect(Collectors.toList());
    }
}

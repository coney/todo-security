package com.thoughtworks.training.todosecurity.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.training.todosecurity.constant.DateTimeConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Todo {
    @Id
    @GeneratedValue
    private Integer id;

    private String action;

    private Status status;

    @JsonFormat(pattern = DateTimeConstants.DATE_FORMAT)
    @Column(columnDefinition = "DATE")
    private LocalDate dueDate;

    @ManyToMany
    @JoinTable(name = "todo_tag", joinColumns = @JoinColumn(name = "todo_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @Cascade(CascadeType.PERSIST)
    @JsonIgnore
    private List<Tag> tags;


    @JsonProperty("tags")
    public List<String> getTagNames() {
        return tags.stream().map(Tag::getName).collect(Collectors.toList());
    }

    @JsonProperty("tags")
    public void setTagNames(List<String> tags) {
        this.tags = tags.stream().map(name -> new Tag(null, name)).collect(Collectors.toList());
    }

    public static enum Status {
        Todo,
        InProgress,
        Blocked
    }
}

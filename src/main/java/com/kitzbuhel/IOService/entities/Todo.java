package com.kitzbuhel.IOService.entities;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "todos")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column(name = "email")
    @Getter
    private String email;

    @Column(name = "description")
    @Getter
    private String description;

    @Column(name = "done")
    @Getter
    private Boolean done;

    public Todo(Long id, String email, String description, Boolean done) {
        this.id = id;
        this.email = email;
        this.description = description;
        this.done = done;
    }

    public Todo() {

    }
}

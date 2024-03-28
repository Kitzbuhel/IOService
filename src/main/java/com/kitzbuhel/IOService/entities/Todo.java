package com.kitzbuhel.IOService.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "todos")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column(name = "email")
    @Getter
    @Setter
    private String email;

    @Column(name = "description")
    @Getter
    @Setter
    private String description;

    @Column(name = "done")
    @Getter
    @Setter
    private Boolean done = false;

    public Todo(String email, String description) {
        this.email = email;
        this.description = description;
    }

    public Todo() {

    }
}

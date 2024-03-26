package com.kitzbuhel.IOService.controllers;

import com.kitzbuhel.IOService.entities.Todo;
import com.kitzbuhel.IOService.repositories.TodosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodosController {
    @Autowired
    private TodosRepository todosRepository;

    @GetMapping("/getByEmail")
    public List<Todo> getTodosByEmail(@RequestBody String email) {
        return todosRepository.findAllByEmail(email);
    }

}

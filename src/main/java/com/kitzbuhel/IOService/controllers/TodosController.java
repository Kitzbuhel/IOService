package com.kitzbuhel.IOService.controllers;

import com.kitzbuhel.IOService.entities.Todo;
import com.kitzbuhel.IOService.repositories.TodosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/todos")
public class TodosController {
    @Autowired
    private TodosRepository todosRepository;

    @GetMapping("/getByEmail")
    public List<Todo> getTodosByEmail(@RequestBody Map<String, String> body) {
        return todosRepository.findAllByEmail(body.get("email"));
    }

    @GetMapping("/getCompletedByEmail")
    public List<Todo> getCompletedTodosByEmail(@RequestBody Map<String, String> body) {
        return todosRepository.findCompletedByEmail(body.get("email"));
    }

    @GetMapping("/getNotCompletedByEmail")
    public List<Todo> getNotCompletedTodosByEmail(@RequestBody Map<String, String> body) {
        return todosRepository.findNotCompletedByEmail(body.get("email"));
    }

    @PostMapping("/addTodo")
    public Todo addTodo(@RequestBody Map<String, String> body) {
        return todosRepository.save(new Todo(body.get("email"), body.get("description")));
    }

    @DeleteMapping("/deleteTodo/{id}")
    public ResponseEntity<String> deleteTodoById(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Todo todo = todosRepository.findById(id).orElseThrow();

        if (!todo.getEmail().equals(body.get("email"))) {
            throw new RuntimeException("Unauthorized");
        }

        todosRepository.delete(todo);

        return ResponseEntity.ok("Deleted");
    }

    @PatchMapping("/updateTodo/{id}")
    public Todo updateTodoById(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Todo todo = todosRepository.findById(id).orElseThrow();

        if (!todo.getEmail().equals(body.get("email"))) {
            throw new RuntimeException("Unauthorized");
        }

        String description = body.get("description");
        if (description != null) {
            todo.setDescription(description);
        }

        return todosRepository.save(todo);
    }

    @PatchMapping("/toggleDone/{id}")
    public Todo toggleDone(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Todo todo = todosRepository.findById(id).orElseThrow();

        if (!todo.getEmail().equals(body.get("email"))) {
            throw new RuntimeException("Unauthorized");
        }

        todo.setDone(!todo.getDone());

        return todosRepository.save(todo);
    }
}

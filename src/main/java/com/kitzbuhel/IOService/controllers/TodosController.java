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
        String email = body.get("email");
        if (email == null)
            throw new RuntimeException("Email is required");

        return todosRepository.findAllByEmail(email);
    }

    @GetMapping("/getCompletedByEmail")
    public List<Todo> getCompletedTodosByEmail(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        if (email == null)
            throw new RuntimeException("Email is required");

        return todosRepository.findCompletedByEmail(email);
    }

    @GetMapping("/getNotCompletedByEmail")
    public List<Todo> getNotCompletedTodosByEmail(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        if (email == null)
            throw new RuntimeException("Email is required");

        return todosRepository.findNotCompletedByEmail(email);
    }

    @PostMapping("/addTodo")
    public Todo addTodo(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        if (email == null)
            throw new RuntimeException("Email is required");

        String description = body.get("description");
        if (description == null)
            throw new RuntimeException("Description is required");

        return todosRepository.save(new Todo(email, description));
    }

    @DeleteMapping("/deleteTodo/{id}")
    public ResponseEntity<String> deleteTodoById(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Todo todo = todosRepository.findById(id).orElseThrow();
        String email = body.get("email");
        if (email == null)
            throw new RuntimeException("Email is required");

        if (!todo.getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized");
        }

        todosRepository.delete(todo);

        return ResponseEntity.ok("Deleted");
    }

    @PatchMapping("/updateTodo/{id}")
    public Todo updateTodoById(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Todo todo = todosRepository.findById(id).orElseThrow();
        String email = body.get("email");
        if (email == null)
            throw new RuntimeException("Email is required");

        if (!todo.getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized");
        }

        String description = body.get("description");
        if (description != null) {
            todo.setDescription(description);
        } else {
            throw new RuntimeException("Description is required");
        }

        return todosRepository.save(todo);
    }

    @PatchMapping("/toggleDone/{id}")
    public Todo toggleDone(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Todo todo = todosRepository.findById(id).orElseThrow();
        String email = body.get("email");
        if (email == null)
            throw new RuntimeException("Email is required");

        if (!todo.getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized");
        }

        todo.setDone(!todo.getDone());

        return todosRepository.save(todo);
    }

    @DeleteMapping("/deleteCompleted")
    public ResponseEntity<String> deleteCompleted(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        if (email == null)
            throw new RuntimeException("Email is required");

        List<Todo> todos = todosRepository.findCompletedByEmail(email);
        todosRepository.deleteAll(todos);
        return ResponseEntity.ok("Deleted");
    }
}

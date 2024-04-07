package com.kitzbuhel.IOService.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kitzbuhel.IOService.entities.Todo;
import com.kitzbuhel.IOService.repositories.TodosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/todos")
public class TodosController {
    @Autowired
    private TodosRepository todosRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/getByEmail")
    public ResponseEntity<String> getTodosByEmail(@RequestBody Map<String, String> body) throws JsonProcessingException {
        Map<String, String> response = new HashMap<>();
        String email = body.get("email");
        if (email == null) {
            response.put("response", "Email is required");
            return new ResponseEntity<>(objectMapper.writeValueAsString(response), HttpStatus.BAD_REQUEST);
        }

        response.put("response", objectMapper.writeValueAsString(todosRepository.findAllByEmail(email)));
        return new ResponseEntity<>(objectMapper.writeValueAsString(response), HttpStatus.OK);
    }

    @GetMapping("/getCompletedByEmail")
    public ResponseEntity<String> getCompletedTodosByEmail(@RequestBody Map<String, String> body) throws JsonProcessingException {
        Map<String, String> response = new HashMap<>();
        String email = body.get("email");
        if (email == null) {
            response.put("response", "Email is required");
            return new ResponseEntity<>(objectMapper.writeValueAsString(response), HttpStatus.BAD_REQUEST);
        }

        response.put("response", objectMapper.writeValueAsString(todosRepository.findCompletedByEmail(email)));
        return new ResponseEntity<>(objectMapper.writeValueAsString(response), HttpStatus.OK);
    }

    @GetMapping("/getNotCompletedByEmail")
    public ResponseEntity<String> getNotCompletedTodosByEmail(@RequestBody Map<String, String> body) throws JsonProcessingException {
        Map<String, String> response = new HashMap<>();
        String email = body.get("email");
        if (email == null) {
            response.put("response", "Email is required");
            return new ResponseEntity<>(objectMapper.writeValueAsString(response), HttpStatus.BAD_REQUEST);
        }

        response.put("response", objectMapper.writeValueAsString(todosRepository.findNotCompletedByEmail(email)));
        return new ResponseEntity<>(objectMapper.writeValueAsString(response), HttpStatus.OK);
    }

    @PostMapping("/addTodo")
    public ResponseEntity<String> addTodo(@RequestBody Map<String, String> body) throws JsonProcessingException {
        Map<String, String> response = new HashMap<>();
        String email = body.get("email");
        if (email == null) {
            response.put("response", "Email is required");
            return new ResponseEntity<>(objectMapper.writeValueAsString(response), HttpStatus.BAD_REQUEST);
        }

        String description = body.get("description");
        if (description == null) {
            response.put("response", "Description is required");
            return new ResponseEntity<>(objectMapper.writeValueAsString(response), HttpStatus.BAD_REQUEST);
        }

        response.put("response", objectMapper.writeValueAsString(todosRepository.save(new Todo(email, description))));
        return new ResponseEntity<>(objectMapper.writeValueAsString(response), HttpStatus.OK);
    }

    @DeleteMapping("/deleteTodo/{id}")
    public ResponseEntity<String> deleteTodoById(@PathVariable Long id, @RequestBody Map<String, String> body) throws JsonProcessingException {
        Map<String, String> response = new HashMap<>();
        Todo todo = todosRepository.findById(id).orElse(null);
        if (todo == null) {
            response.put("response", "Todo not found");
            return new ResponseEntity<>(objectMapper.writeValueAsString(response), HttpStatus.NOT_FOUND);
        }

        String email = body.get("email");
        if (email == null) {
            response.put("response", "Email is required");
            return new ResponseEntity<>(objectMapper.writeValueAsString(response), HttpStatus.BAD_REQUEST);
        }

        if (!todo.getEmail().equals(email)) {
            response.put("response", "Unauthorized");
            return new ResponseEntity<>(objectMapper.writeValueAsString(response), HttpStatus.UNAUTHORIZED);
        }

        todosRepository.delete(todo);

        response.put("response", "Todo deleted successfully");
        return new ResponseEntity<>(objectMapper.writeValueAsString(response), HttpStatus.OK);
    }

    @PatchMapping("/updateTodo/{id}")
    public ResponseEntity<String> updateTodoById(@PathVariable Long id, @RequestBody Map<String, String> body) throws JsonProcessingException {
        Map<String, String> response = new HashMap<>();
        Todo todo = todosRepository.findById(id).orElse(null);
        if (todo == null) {
            response.put("response", "Todo not found");
            return new ResponseEntity<>(objectMapper.writeValueAsString(response), HttpStatus.NOT_FOUND);
        }

        String email = body.get("email");
        if (email == null) {
            response.put("response", "Email is required");
            return new ResponseEntity<>(objectMapper.writeValueAsString(response), HttpStatus.BAD_REQUEST);
        }

        if (!todo.getEmail().equals(email)) {
            response.put("response", "Unauthorized");
            return new ResponseEntity<>(objectMapper.writeValueAsString(response), HttpStatus.UNAUTHORIZED);
        }

        String description = body.get("description");
        if (description != null) {
            todo.setDescription(description);
        } else {
            response.put("response", "Description is required");
            return new ResponseEntity<>(objectMapper.writeValueAsString(response), HttpStatus.BAD_REQUEST);
        }

        response.put("response", objectMapper.writeValueAsString(todosRepository.save(todo)));
        return new ResponseEntity<>(objectMapper.writeValueAsString(response), HttpStatus.OK);
    }

    @PatchMapping("/toggleDone/{id}")
    public ResponseEntity<String> toggleDone(@PathVariable Long id, @RequestBody Map<String, String> body) throws JsonProcessingException {
        Map<String, String> response = new HashMap<>();
        Todo todo = todosRepository.findById(id).orElse(null);
        if (todo == null) {
            response.put("response", "Todo not found");
            return new ResponseEntity<>(objectMapper.writeValueAsString(response), HttpStatus.NOT_FOUND);
        }

        String email = body.get("email");
        if (email == null) {
            response.put("response", "Email is required");
            return new ResponseEntity<>(objectMapper.writeValueAsString(response), HttpStatus.BAD_REQUEST);
        }

        if (!todo.getEmail().equals(email)) {
            response.put("response", "Unauthorized");
            return new ResponseEntity<>(objectMapper.writeValueAsString(response), HttpStatus.UNAUTHORIZED);
        }

        todo.setDone(!todo.getDone());

        response.put("response", objectMapper.writeValueAsString(todosRepository.save(todo)));
        return new ResponseEntity<>(objectMapper.writeValueAsString(response), HttpStatus.OK);
    }

    @DeleteMapping("/deleteCompleted")
    public ResponseEntity<String> deleteCompleted(@RequestBody Map<String, String> body) throws JsonProcessingException {
        Map<String, String> response = new HashMap<>();
        String email = body.get("email");
        if (email == null) {
            response.put("response", "Email is required");
            return new ResponseEntity<>(objectMapper.writeValueAsString(response), HttpStatus.BAD_REQUEST);
        }

        List<Todo> todos = todosRepository.findCompletedByEmail(email);
        todosRepository.deleteAll(todos);

        response.put("response", "Completed todos deleted successfully");
        return new ResponseEntity<>(objectMapper.writeValueAsString(response), HttpStatus.OK);
    }
}

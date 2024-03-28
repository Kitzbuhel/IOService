package com.kitzbuhel.IOService.repositories;

import com.kitzbuhel.IOService.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodosRepository extends JpaRepository<Todo, Long> {
    @Query
    (value = "SELECT * FROM todos t WHERE t.email = ?1",
     nativeQuery = true)
    List<Todo> findAllByEmail(String email);

    @Query
    (value = "SELECT * FROM todos t WHERE t.email = ?1 AND t.done = true",
     nativeQuery = true)
    List<Todo> findCompletedByEmail(String email);

    @Query
    (value = "SELECT * FROM todos t WHERE t.email = ?1 AND t.done = false",
     nativeQuery = true)
    List<Todo> findNotCompletedByEmail(String email);
}

package com.enigmacamp.coop.controller;

import com.enigmacamp.coop.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Map;

@RestController
public class HelloController {

    private List<Map<String, Object>> users = List.of(
            Map.of(
                    "id", 1,
                    "name", "zee"
            ),
            Map.of(
                    "id", 2,
                    "name", "gracia"
            )
    );

    private List<String> student = List.of("Ella", "Indira", "Raisha", "Callie", "lia", "lyn", "amanda");

    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }

    @GetMapping("/users")
    public List<Map<String, Object>> getListUsers(){
        return users;
    }

    @GetMapping("/users/{name}")
    public String getUserById(@PathVariable("name") String name ){
        return "hi, " + name;
    }

    @GetMapping("/student")
    public List<String> getListStudent(@RequestParam String search) {
        return student.stream().filter(student -> student.toLowerCase().contains(search.toLowerCase())).toList();
    }

    @PostMapping("/user")
    public Map<Object, Object> createUser(@RequestBody User user){
        return Map.of(
                        "message", "Success created user",
                        "name", user
                );
    }

    @DeleteMapping
    public void deleteStudent(){

    }

    @PutMapping
    public void updateStudent(){

    }

//    Master -> intro
//    01-JPA -> setup entity, repository, migration
//    02-ResponseEntity -> Data type for response Json more simplyfy
//    03-Pagination -> limited data
//    04-Pageable
//    05-DTO -> Data transfer object
//    06-Transaction -> include Relationbetween table
//    07-validation
//    08-spesification
//    09-Security
//    10-Rest Client
//    11-Swagger

}

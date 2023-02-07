package com.example.cinema.controller;

import com.example.cinema.dto.UserDTO;
import com.example.cinema.usecase.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserServices userServices;

    @GetMapping("/api/users")
    public Flux<UserDTO> finAll() {
        return userServices.findAll();
    }

    @GetMapping("/api/users/{id}")
    public Mono<UserDTO> findById(@PathVariable(value = "id") String userId) {
        return userServices.findById(userId);
    }

    @PostMapping("/api/users")
    public Mono<UserDTO> saveUser(@RequestBody UserDTO user) {
        return userServices.saveUser(user);
    }

    @DeleteMapping("/api/users/{id}")
    public Mono<Void> deleteUser(@PathVariable(value = "id") String userId) {
        return userServices.deleteUser(userId);
    }
}

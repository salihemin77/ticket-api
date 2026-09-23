package com.example.ticket_api.controller;

import com.example.ticket_api.entity.User;
import com.example.ticket_api.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public UserController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> findAll() {
        return userService.findAll();

    }
    @GetMapping("/users/{id}")
    public User findById(@PathVariable Integer id) {
        return userService.findById(id);
    }
    @PostMapping("/users")
    public User create(@RequestBody User user) {
      String encodedPassword = passwordEncoder.encode(user.getPassword());
      user.setPassword(encodedPassword);
      return userService.save(user);
    }
    @PutMapping("/users/{id}")
    public User update(@PathVariable Integer id, @RequestBody User user) {
        user.setId(id);
        return userService.update(user);
    }
    @DeleteMapping("/users/{id}")
    public void deleteById(@PathVariable Integer id) {
        userService.deleteById(id);
    }




}

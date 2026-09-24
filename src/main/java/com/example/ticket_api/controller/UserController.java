package com.example.ticket_api.controller;

import com.example.ticket_api.dto.UserResponseDTO;
import com.example.ticket_api.entity.User;
import com.example.ticket_api.mapper.UserMapper;
import com.example.ticket_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private UserMapper userMapper;

    public UserController(PasswordEncoder passwordEncoder, UserMapper userMapper, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserResponseDTO> findAll() {
        return userService.findAll().stream().map(userMapper::toDTO).toList();

    }
    @GetMapping("/users/{id}")
    public UserResponseDTO findById(@PathVariable Integer id) {
        return userMapper.toDTO(userService.findById(id));
    }
    @PostMapping("/users")
    public User create( @Valid @RequestBody User user) {
      String encodedPassword = passwordEncoder.encode(user.getPassword());
      user.setPassword(encodedPassword);
      return userService.save(user);
    }
    @PutMapping("/users/{id}")
    public User update(@PathVariable Integer id,@Valid @RequestBody User user) {
        user.setId(id);
        return userService.update(user);
    }
    @DeleteMapping("/users/{id}")
    public void deleteById(@PathVariable Integer id) {
        userService.deleteById(id);
    }




}

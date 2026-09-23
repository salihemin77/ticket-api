package com.example.ticket_api.service;

import com.example.ticket_api.entity.User;

import java.util.List;

public interface UserService  {
    User save(User user);
    User findById(Integer id);
    List<User> findAll();
    void  deleteById(Integer id);
    User update(User user);
}

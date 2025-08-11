package org.example.springbootsecurity.service;

import org.example.springbootsecurity.dto.UserDto;
import org.example.springbootsecurity.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    User findByEmail(String email);
    List<UserDto> findAllUsers();
}

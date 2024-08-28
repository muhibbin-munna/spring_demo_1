package com.example.project1.service;



import com.example.project1.entity.User;
import com.example.project1.model.LoginMessage;

public interface UserService {
    User registerUser(User user);

    User findUserByEmail(String email);
    LoginMessage findByEmailAndPassword(String email, String password);

    User registerAdmin(User user);
}

package com.example.project1.model;

import com.example.project1.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginMessage {
    String message;
    Boolean status;
    String token;
    User user;

}
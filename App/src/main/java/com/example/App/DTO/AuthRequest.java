package com.example.App.DTO;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthRequest {
    // Getters & Setters
    private String username;
    private String password;

    public AuthRequest() {}

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

}


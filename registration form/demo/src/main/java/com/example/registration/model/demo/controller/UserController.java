package com.example.registration.model.demo.controller;

import com.example.registration.model.demo.entity.UserDetails;
import com.example.registration.model.demo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class UserController {
    @Autowired
private UserRepository repository;

    @GetMapping("/register")
    @ResponseBody
    public String home(){
        return "index";
    }
    @PostMapping("/register")
    public String register(@ModelAttribute UserDetails u, HttpSession session){
        System.out.println(u);
        repository.save(u);
        session.setAttribute("message", "User Register Successfully.");

        return "redirect:/";
    }
}

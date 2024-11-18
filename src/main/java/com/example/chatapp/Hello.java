package com.example.chatapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class Hello {

    @GetMapping("/")
    public String showForm() {
        return "index";
    }
    @GetMapping("/test")
    public String testEndpoint() {
        return "Test successful!";
    }
}

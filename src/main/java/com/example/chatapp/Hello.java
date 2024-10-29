package com.example.chatapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Hello {

    @GetMapping("/")
    public String showForm() {
        return "index";
    }
    @PostMapping("/test")
    public String testEndpoint() {
        return "Test successful!";
    }
}

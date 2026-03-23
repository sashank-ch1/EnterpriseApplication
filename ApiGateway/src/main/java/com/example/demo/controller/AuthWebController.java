package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthWebController {

    // Redirect GET /auth/login to Spring Security's default login page /login // Edited
    @GetMapping("/auth/login")
    public String loginPage() {
        return "redirect:/login"; // Edited
    }
}
package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	@Autowired
public PaymentService payserv;
	
	@PostMapping("/pay")
    public String pay() {
        return payserv.processPayment();
    }
}

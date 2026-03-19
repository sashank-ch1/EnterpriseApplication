package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
	public OrderService service;
	
    @PostMapping("/{productId}")
    public String order(@PathVariable String productId) {
        return service.placeOrder(productId);
    }
}

package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.InventoryService;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
@Autowired
public InventoryService invssrev;
	@GetMapping("/check/{productId}")
    public boolean checkStock(@PathVariable String productId) {
        return invssrev.isInStock(productId);
    }
}

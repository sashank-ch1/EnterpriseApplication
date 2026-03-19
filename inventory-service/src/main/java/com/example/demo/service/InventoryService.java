package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.entity.Inventory;
import com.example.demo.repository.InventoryRepo;

@Service
public class InventoryService {
	@Autowired
public InventoryRepo invrepo;
	public boolean isInStock(@PathVariable String productId) {
        Inventory inv = invrepo.findByProductId(productId).orElse(null);
        return inv != null && inv.getQunty() > 0;
    }
}

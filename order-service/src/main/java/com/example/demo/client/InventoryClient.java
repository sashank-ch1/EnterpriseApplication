package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="inventory-service")
public interface InventoryClient {
	 @GetMapping("/inventory/check/{productId}")
	    boolean checkStock(@PathVariable String productId);
}

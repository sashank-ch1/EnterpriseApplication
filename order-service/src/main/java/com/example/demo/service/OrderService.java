package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.client.InventoryClient;
import com.example.demo.client.PaymentClient;
import com.example.demo.client.ProductClient;
import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Orders;
import com.example.demo.repository.OrdersRepository;

@Service
public class OrderService {

    @Autowired
    private OrdersRepository ordrepo;

    @Autowired
    private InventoryClient invcli;

    @Autowired
    private PaymentClient paycli;
    
    @Autowired
    private ProductClient productClient;

    public String placeOrder(String productId) {

    	ProductDTO product = productClient.getProduct(productId);
        // Step 1: Check inventory
        boolean inStock = invcli.checkStock(productId);

        if (!inStock) {
            return "Product Out of Stock";
        }

        // Step 2: Payment
        String payment = paycli.pay();

        // Step 3: Save order
        Orders order = new Orders();
        order.setProductid(product.getProductId());
        order.setProductid(product.getName());
        order.setPrice(product.getPrice());

        ordrepo.save(order);

        return "Order Placed Successfully + " + payment;
    }
}

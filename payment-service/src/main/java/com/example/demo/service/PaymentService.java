package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Payment;
import com.example.demo.repository.PaymentRepo;

@Service
public class PaymentService {
    @Autowired
	public PaymentRepo payrepo;
    
    public String processPayment() {
        Payment p = new Payment();
       p.setStatus("SUCCESS");
        if(p.getStatus().contains("success")) {
        	payrepo.save(p);
        }
        return "Payment Successful";
    }
}
